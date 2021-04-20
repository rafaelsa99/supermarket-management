
package SAManager;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IManager_Control {

    public void start(int nCustomers);
    public void suspend();
    public void resume();
    public void stop();
    public void end();
    public void auto(int timeout);
    public void manual();
    public void step();
}
