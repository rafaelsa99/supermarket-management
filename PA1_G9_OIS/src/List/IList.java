
package List;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IList {
    public void in( int customerId );
    public void out(int customerId);
    public int outFirst();
    public void removeAll();
    public int getCount();
}
