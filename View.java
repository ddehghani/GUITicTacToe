public abstract class View implements Observer
{	
	protected Controller controller;
	
	public View(Controller controller)
	{
		this.controller = controller;
	}
}