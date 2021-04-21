
package List;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public class List implements IList{
    // número máximo de customer (dimensão dos arrays)
    private final int maxCustomers;
    // array para guardar os ids
    private final int customerId[];
    // número de customers na list
    private int count;
    // flag que indica se é necessário manter o array ordenado
    private final boolean order;
    
    public List( int maxCustomers, boolean order) {
        this.maxCustomers = maxCustomers;
        customerId = new int[ maxCustomers ];
        for (int i = 0; i < customerId.length; i++)
            customerId[i] = -1;
        count = 0;
        this.order = order;
    }
    
    public List( int maxCustomers ) {
        this(maxCustomers, false);
    }

    @Override
    public void in(int customerId) {
        int idx = count;
        // se for necessário manter a ordem do array
        if(order){
            int i;
            for(i = count - 1; (i >= 0 && this.customerId[i] > customerId); i--)
                this.customerId[i + 1] = this.customerId[i];
            this.customerId[i + 1] = customerId;
            idx = i;
        } else 
            this.customerId[count] = customerId;
        // incrementar número customers na list
        count++;    
    }

    @Override
    public void out(int customerId) {
        //Remove element from list
        for(int i = 0; i < count; i++){
            if(this.customerId[i] == customerId){
                // shifting elements
                for(int j = i; j < count - 1; j++){
                    this.customerId[j] = this.customerId[j+1];
                }
                break;
            }
        }
        // decrementar número de customers na list
        count--;
    }

    @Override
    public int outFirst() {
        int cId = this.customerId[0];
        for(int i = 0; i < count - 1; i++)
            this.customerId[i] = this.customerId[i+1];
        // decrementar número de customers na list
        count--;
        return cId;
    }
    
    @Override
    public void removeAll() {
        for (int i = 0; i < customerId.length; i++)
            customerId[i] = -1;
        count = 0;
    }
    
    @Override
    public int getCount() {
        return count;
    }
}
