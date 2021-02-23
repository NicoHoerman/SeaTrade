package Shared;

public class Response {

	public String content;
	public boolean isError;
	
	public Response(String content) {
		this.content = content;
		this.isError = false;
	}
}
