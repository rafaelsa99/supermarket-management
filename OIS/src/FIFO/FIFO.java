package FIFO;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Não pretende ser a versão final do FIFO. Cada grupo deve usar o FIFO que mais 
 * lhe convier. Tb pode e deve alterar o aqui apresentado.
 * 
 * Nesta versão, os Customers entram no fifo e dp ficam a aguardar autorização
 * para sair. Tudo isto ocorre no método in.
 * 
 * A autorização para sair é dada pela Manager em out. Dp de ser dada a 
 * autorização para sair, Manager fica a aguardar que o Customer confirme a
 * saída.
 * 
 * No caso de o fifo ser criado com a dimensão = 99, não seria necessário 
 * verificar se está cheio.
 * 
 * @author Rafael Sá (104552), Luís Laranjeira (81526) (Based on the professor implementation)
 */
public class FIFO implements IFIFO {
    
    // lock para acesso à área partilhada 
    private final ReentrantLock rl = new ReentrantLock( true );

    // array para ids dos customers
    private final int customerId[];
    // array para Condition de bloqueio (uma por customer)
    private final Condition cStay[];
    // Conditio  de bloqueio de fifo cheio
    private final Condition cFull;
    // Condition de bloqueio de fifo vazio
    private final Condition cEmpty;
    // Condition de bloqueio para aguardar saída do fifo
    private final Condition cLeaving;
    // array para condição de bloqueio de cada customer
    private final boolean leave[];
    // número máximo de customer (dimensão dos arrays)
    private final int maxCustomers;
    
    // próxima posição de escrita no fifo
    private int idxIn;
    // próxima posição de leitura no fifo
    private int idxOut;
    // número de customers no fifo
    private int count = 0;
    // flag que indica se simulação está suspensa
    private boolean suspend;
    // flag que indica se está em curso a operação de remover todos elementos do fifo
    private boolean removeAll;
    
    public FIFO( int maxCustomers ) {
        this.maxCustomers = maxCustomers;
        customerId = new int[ maxCustomers ];
        cStay = new Condition[ maxCustomers ];
        leave = new boolean[ maxCustomers ];
        for ( int i = 0; i < maxCustomers; i++ ) {
            cStay[ i ] = rl.newCondition();
            leave[ i ] = false;
        }
        cFull = rl.newCondition();
        cEmpty = rl.newCondition();
        cLeaving = rl.newCondition();
        idxIn = 0;
        idxOut = 0; 
        suspend = false;
        removeAll = false;
    }
    // Entrada no fifo. O NOTA: o thead Customer pode ficar bloqueado à espera de 
    // autorização para sair do fifo
    @Override
    public void in( int customerId ) {
        try {
            // garantir acesso em exclusividade
            rl.lock();
            
            // se fifo cheio, espera na Condition cFull
            while ( count == maxCustomers  || suspend)
                cFull.await();
            if(removeAll)
                return;
            
            // usar variável local e incrementar apontador de escrita
            int idx = idxIn;
            idxIn = (++idxIn) % maxCustomers;
            // inserir customer no fifo
            this.customerId[ idx ] = customerId;
            
            // o fifo poderá estar vazio, pelo q neste caso a Customer poderá
            // estar à espera q um Customer chegue. Necessério avisar Manager
            // q se enconra em espera na Condition cEmpty
            if ( count == 0 )
                cEmpty.signal();
            
            // incrementar número customers no fifo
            count++;
            // ciclo à espera de autorização para sair do fifo
            while ( !leave[ idx ] || suspend)
                // qd se faz await, permite-se q outros thread tenham acesso
                // à zona protegida pelo lock
                cStay[ idx ].await();

            // Customer selecionado está a sair do fifo
            
            // atualizar variável de bloqueio
            leave[ idx ] = false;
            // avisar Manager que Customer vai sair. Manager espera na
            // Condition cLeaving
            cLeaving.signal();
            
            // se fifo estava cheio, acordar Customer q esteja à espera de entrar
            if ( count == maxCustomers )
                cFull.signal();
            // decrementar número de customers no fifo
            count--;

        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
        // Customer a sair não só do fifo como tb a permitir q outros
        // threads possam entrar na zona crítica
    }
    // acordar o customer q há mais tempo está no fifo (sem ter sido desbloqueado!)
    @Override
    public void out() {
        try {
            rl.lock();
            if(removeAll)
                return;
            // se fifo vazio, espera
            while ( count == 0  || suspend)
                cEmpty.await();
            int idx = idxOut;
            // atualizar idxOut
            idxOut = (++idxOut) % maxCustomers; 
            // autorizar a saída do customer q há mais tempo está no fifo
            leave[ idx ] = true;
            // acordar o customer
            cStay[ idx ].signal();
            // aguardar até q Customer saia do fifo
            while ( leave[ idx ] == true || suspend)
                // qd se faz await, permite-se q outros thread tenham acesso
                // à zona protegida pelo lock
                cLeaving.await();  
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
    }

    @Override
    public void suspend() {
        try {
            rl.lock();
            suspend = true;
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
    }

    @Override
    public void resume() {
        try {
            rl.lock();
            suspend = false;
            for (int i = 0; i < maxCustomers; i++)
                cStay[i].signal();
            cFull.signal();
            cEmpty.signal();
            cLeaving.signal();
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
    }

    @Override
    public void removeAll() {
        try {
            rl.lock();
            suspend = false;
            removeAll = true;
            for (int i = 0; i < maxCustomers; i++) {
                leave[i] = true;
                cStay[i].signal();
            }
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
    }

    public void resetFIFO() {
        try {
            rl.lock();
            for ( int i = 0; i < maxCustomers; i++ )
                leave[ i ] = false;
            idxIn = 0;
            idxOut = 0; 
            suspend = false;
            removeAll = false;
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
    }

    public boolean isEmpty() {
        try {
            rl.lock();
            if(count > 0)
                return false;
        } catch ( Exception ex ) {}
        finally {
            rl.unlock();
        }
        return true;
    }
    
}
