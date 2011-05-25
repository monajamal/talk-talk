package utils;

import java.awt.Font;

@SuppressWarnings("serial")
public class Fonte extends Font {
	
	public Fonte(Font font) {
		super(font);
	}
	public Fonte(String fontName, int style, int size) {
		super(fontName,style,size);
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setStyle(int style) {
		this.style = style;
	}
	public void setSize(int size) {
		this.size = size;
	}
}