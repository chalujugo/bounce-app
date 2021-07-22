/*
 *	===============================================================================
 *	TableModelAdapter.java : Creates a table model.
 *  CHALU JUGO CJUG294 6/6/21
 *	=============================================================================== */

import javax.swing.table.AbstractTableModel;

class TableModelAdapter extends AbstractTableModel{
    
    private Shape nestedShape;
    private static String[] columnNames = {"Type", "X-pos", "Y-pos", "Width", "Height"};
    
    public TableModelAdapter(Shape ns) {
        nestedShape = ns;
    }
    
    public int getColumnCount() {
        return columnNames.length;
    }
    
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public int getRowCount() {
        NestedShape ns = (NestedShape) nestedShape;
        return ns.getSize();
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        Shape sh = nestedShape;
        Object res = null;
        if(nestedShape instanceof NestedShape) {
            NestedShape ns = (NestedShape) nestedShape;
            sh = ns.getShapeAt(rowIndex);
        }
        switch(columnIndex) {
            case 0:
                res = sh.getClass().getName();
                break;
            case 1:
                res = sh.getX();
                break;
            case 2:
                res = sh.getY();
                break;
            case 3:
                res = sh.getWidth();
                break;
            case 4:
                res = sh.getHeight();
                break;
        }
        
        return res;
        
    }
    
    public void setNestedShape(Shape s) {
        nestedShape = s;
        fireTableDataChanged();
    }
    
}