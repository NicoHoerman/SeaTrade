package Shared;

public interface IResponseHandler {

	public void processError(Response response);
	public void processResponse(Response response);
}
