
package SAPaymentBox;

/**
 *
 * @author Rafael Sá (104552), Luís Laranjeira (81526)
 */
public interface IPaymentBox_Control {

    public void start();
    
    public void suspend();

    public void resume();

    public void stop();

    public void end();
    
    public void setTimeoutPayment(int timeoutPayment);
}
