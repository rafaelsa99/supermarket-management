
package SACorridor;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface ICorridor_Control {
    public void start();
    
    public void suspend();

    public void resume();

    public void stop();

    public void end();

    public void setTimeoutMovement(int timeoutMovement);
}
