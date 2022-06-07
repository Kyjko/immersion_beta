package inventory;

public class Item {
	
	Type type;
	public int value;
	
	public Item(Type type) {
		this.type = type;
		if(type == Type.Fish) {
			value = 10;
		}else if( type == Type.Junk) {
			value = 2;
		} else if(type == Type.Gold) {
			value = 100;
		}
		else if(type == Type.Tablet) {
			value=0;
		}
	}

}
