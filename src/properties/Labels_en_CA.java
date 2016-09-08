package properties;

import java.util.ListResourceBundle;

public class Labels_en_CA extends ListResourceBundle {
	protected Object[][] getContents() {
		return new Object[][] {
			{"hello", new StringBuilder("Hello to you Java")},
			{"elevator", new StringBuilder("lift")}
		};
	}
}