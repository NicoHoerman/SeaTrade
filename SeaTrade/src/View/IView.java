package View;

public interface IView {

	public void OutputData(String data);
	public String nextInput() throws Exception;
	public void shutdown();
}
