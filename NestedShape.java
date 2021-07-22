import java.awt.Color;
import java.util.ArrayList;
/*
 *  ============================================================================================
 *  Creates a nested shape object.
 *  CHALU JUGO CJUG294 6/6/21
 *  ============================================================================================
 */
public class NestedShape extends RectangleShape {
    private ArrayList<Shape> nestedShapes = new ArrayList<Shape>();
    private static ShapeType nextShapeType = ShapeType.NESTED;
    
    public NestedShape() {
        super();
	
    }
    
    public NestedShape(ArrayList<Shape> shapes, Color fc, Color bc) {
        super(0, 0, Shape.DEFAULT_MARGIN_WIDTH, Shape.DEFAULT_MARGIN_HEIGHT, Shape.DEFAULT_MARGIN_WIDTH, Shape.DEFAULT_MARGIN_HEIGHT, bc, fc, PathType.BOUNCE, Shape.DEFAULT_TEXT);
        for(Shape s : shapes) {
            s.setParent(this);
        }
        nestedShapes = shapes;
    }
    
    public NestedShape(int x, int y, int w, int h, int mw, int mh, Color bc, Color fc, PathType pt) {
        super(x ,y ,w, h ,mw ,mh, bc, fc, pt);
        nextShapeType = nextShapeType.next();
        createAddInnerShape(nextShapeType);
    }  
    
    public NestedShape(int x, int y, int w, int h, int mw, int mh, Color bc, Color fc, PathType pt, String txt) {
        super(x ,y ,w, h ,mw ,mh, bc, fc, pt, txt);
        nextShapeType = nextShapeType.next();
        createAddInnerShape(nextShapeType);

    }
    
    public void draw(Painter painter){
        painter.setPaint(Color.black);
        painter.drawRect(x,y,width,height);
        painter.translate(x,y);

        for (Shape s : nestedShapes) {
            s.draw(painter); 

            }
        painter.translate(-x,-y);

    }
    
    public void move() {
        super.move();
        for(Shape sh : nestedShapes) {
            sh.move();
        }
    }
    
    
    public void createAddInnerShape(ShapeType st) {
        Shape s = null;
        switch (st) {
            case RECTANGLE: {
                s = new RectangleShape(0, 0, this.getWidth() /2, this.getHeight() /2, width, height, this.getBorderColor(), this.getFillColor(), PathType.BOUNCE, this.getText());
                break;
            }
            case OVAL: {
                s = new OvalShape(0, 0, this.getWidth() /2, this.getHeight() /2, width, height, this.getBorderColor(), this.getFillColor(), PathType.BOUNCE, this.getText());
                break;
            }
                
            case XRECTANGLE: {
                s = new XRectangleShape(0, 0, this.getWidth() /2, this.getHeight() /2,  width, height, this.getBorderColor(), this.getFillColor(), PathType.BOUNCE, this.getText());
                break;
            }
                
            case SQUARE: {
                s = new SquareShape(0, 0, Math.min(width, height) /2, width, height, this.getBorderColor(), this.getFillColor(), PathType.BOUNCE, this.getText());
                break;
            }
                
            case NESTED: {
                s = new NestedShape(0, 0, width / 2, height / 2, width, height, borderColor, fillColor, PathType.BOUNCE, text);
                break;
            }
        }
        s.setParent(this);
        nestedShapes.add(s);
        
    }
    
    public Shape getShapeAt(int index) {
        return nestedShapes.get(index);
    }
    
    public int getSize() {
        return nestedShapes.size();
    }
    
    public NestedShape getParent() {
        return this.parent;
    }
    

    public void add(Shape s)  {
        nestedShapes.add(s);
        s.setParent(this);

    }
    
    
    public void remove(Shape s) {
        nestedShapes.remove(s);
        s.setParent(null);
    }
    
    public int indexOf(Shape s) {
        return nestedShapes.indexOf(s);
    }
    
    public Shape[] getChildren() {
        Shape[] shapeArray = new Shape[nestedShapes.size()];
        for(int i = 0; i < nestedShapes.size(); i ++) {
            shapeArray[i] = nestedShapes.get(i);
            
        }
        return shapeArray;
    }
    
}