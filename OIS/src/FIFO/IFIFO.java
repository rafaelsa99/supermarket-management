
package FIFO;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IFIFO {
    public void in( int customerId );
    public void out();
    public void suspend();
    public void resume();
    public void removeAll();
}
