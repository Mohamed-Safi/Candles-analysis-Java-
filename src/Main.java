
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Main extends javax.swing.JFrame {
    
    ArrayList<String> Date1;
    ArrayList<String> Hour;
    ArrayList<Double> Hight;
    ArrayList<Double> Low;
    
    ArrayList<Integer> top;
    ArrayList<Integer> bottom;
    ArrayList<Integer> allOf_TB;
    ArrayList<Integer> serial;
    ArrayList<Integer> valid;
    
    DefaultTableModel model;
    
    public Main() {
        initComponents();
        
        Image icon = Toolkit.getDefaultToolkit().getImage(("src\\logo.png"));
        this.setIconImage(icon);
        
        
        this.setResizable(false);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }
    
    public void readeExcle(FileInputStream in){
        
        Date1 = new ArrayList<>();
        Hour = new ArrayList<>();
        Hight = new ArrayList<>();
        Low = new ArrayList<>();
        
        try {
            
            XSSFWorkbook importFile = new XSSFWorkbook(in);
            XSSFSheet sheet1 = importFile.getSheetAt(0);
            
            Iterator<Row> rowIterator = sheet1.iterator();
            
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    if(cell.getColumnIndex()==0){
                        Date1.add(cell.getStringCellValue());
                    }else if(cell.getColumnIndex()==1){
                        Date date = cell.getDateCellValue();
                        DateFormat dateFormat = new SimpleDateFormat("HH:mm",new Locale("en"));
                       
                        String s = dateFormat.format(date);
                        Hour.add(s);
                    }
                    else if(cell.getColumnIndex()==2){
                        Hight.add((Double) cell.getNumericCellValue());
                    }
                    else if(cell.getColumnIndex()==3){
                        Low.add((Double) cell.getNumericCellValue());
                    } 
                }
            }
            in.close();
            fillTable();
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void fillTable(){
        
        model = (DefaultTableModel) jTable1.getModel();
        
        for(int i=0;i<Date1.size();i++){
            
            model.addRow(new Object[]{Date1.get(i),Hour.get(i),Hight.get(i),Low.get(i)});
            
        }
    }
    
    public void checkSerial(){
        
        valid = new ArrayList<>();
        
        
        for(int i=allOf_TB.size()-1;i>=0;i--){
            
            int poiner = allOf_TB.get(i);
            
            if(poiner != -1){
                
                for(int j=0;j<serial.size();j++){
                
                    int x = serial.get(j);
                    
                    if(poiner-x >= 0){
                        
                        poiner = allOf_TB.get(poiner-x);


                        if(poiner == -1){
                            break;
                        }

                        if(j == serial.size()-1){
                            valid.add(i);
                        }
                        
                    }
                    
                }
            }
        }
        
        fillResult();
    }
    
    public void fillResult(){
        
        if(valid != null){
            
            String Da;
            String Ho;
            double hi;
            double lo;
            
            for(int i=valid.size()-1;i>=0;i--){
                
                int x = valid.get(i);
                
                Da = (String) jTable1.getValueAt(x, 0);
                Ho = (String) jTable1.getValueAt(x, 1);
                hi = (Double) jTable1.getValueAt(x, 2);
                lo = (Double) jTable1.getValueAt(x, 3);

                model = (DefaultTableModel) jTable2.getModel();
                model.addRow(new Object[]{Da ,Ho ,hi ,lo});
                
            }
            
        }
        
        
        
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Candles analysis");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Hour", "Hight", "Low"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(90);
        }

        jButton1.setText("Choos File");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("Tops and Bottoms");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jButton3.setText("search");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Hour", "Hight", "Low"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMinWidth(90);
        }

        jLabel1.setText("Created by : Mohammed Safi");

        jLabel2.setText("Whatsapp : +972592261234");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jButton3)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jButton1)
                        .addGap(30, 30, 30)
                        .addComponent(jButton2)
                        .addGap(35, 35, 35)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addGap(16, 16, 16))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        
        
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open File");
        chooser.removeChoosableFileFilter(chooser.getFileFilter());
        FileFilter filter = new FileNameExtensionFilter("Excel File (.xlsx)", "xlsx");
        chooser.setFileFilter(filter);
        
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            File f = chooser.getSelectedFile();
            
            try(FileInputStream in = new FileInputStream(f)){
                
                readeExcle(in);
                
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        
        top = new ArrayList<>();
        bottom = new ArrayList<>();
        allOf_TB = new ArrayList<>();
        
        model = (DefaultTableModel) jTable1.getModel();
        
        for(int i=0;i<jTable1.getRowCount();i++){
            
            if(i!=0 && i<jTable1.getRowCount()-1){
                
                double prev = (Double) jTable1.getValueAt(i-1, 2);
                double defult = (Double) jTable1.getValueAt(i, 2);
                double next = (Double) jTable1.getValueAt(i+1, 2);

                double prev2 = (Double) jTable1.getValueAt(i-1, 3);
                double defult2 = (Double) jTable1.getValueAt(i, 3);
                double next2 = (Double) jTable1.getValueAt(i+1, 3);

                if(defult>prev && defult>next){

                    top.add(i);
                    allOf_TB.add(i);

                }else if( prev == defult && next == defult){

                    top.add(i);
                    allOf_TB.add(i);

                }
                else if(defult2<prev2 && defult2<next2){

                    bottom.add(i);
                    allOf_TB.add(i);

                }else if(prev2 == defult2 && next2 == defult2){

                    bottom.add(i);
                    allOf_TB.add(i);

                }
                else{
                    allOf_TB.add(-1);
                }
                
            }else{
                allOf_TB.add(-1);
            }
        }
        
        
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                
                for(int i=1;i<table.getRowCount();i++) {
                if(top.contains(row))  
                {  
                    setForeground(Color.black);          
                    setBackground(new Color(52, 152, 219));              
                }
                else if(bottom.contains(row)){
                    
                    setForeground(Color.black);          
                    setBackground(new Color(231, 76, 60)); 
                    
                }
                else 
                {      
                    setBackground(Color.white);      
                    setForeground(Color.black);      
                }   

                return c;
            }

                return this;
            }
        });
        
        JViewport viewport = (JViewport)jTable1.getParent();
        Rectangle rect = jTable1.getCellRect(100, 7, true);
        Point pt = viewport.getViewPosition();
        rect.setLocation(rect.x-pt.x, rect.y-pt.y);
        jTable1.scrollRectToVisible(rect);
        
        viewport = (JViewport)jTable1.getParent();
        rect = jTable1.getCellRect(0, 7, true);
        pt = viewport.getViewPosition();
        rect.setLocation(rect.x-pt.x, rect.y-pt.y);
        jTable1.scrollRectToVisible(rect);
        
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        
        
        model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        
        
        serial = new ArrayList<>();
        
        String userSerila = jTextField1.getText();
        String temp = "";
        char c; 
        for(int i=0;i<userSerila.length();i++){
            
            c = userSerila.charAt(i);
            if('-'!=c){
                temp = temp+c;
                
            }else{
                int x = Integer.parseInt(temp);
                serial.add(x);
                temp = "";
            }
            if(i == userSerila.length()-1){
                
                int x = Integer.parseInt(temp);
                serial.add(x);
                temp = "";
            }
            
        }
        
        checkSerial();
        
    }//GEN-LAST:event_jButton3MouseClicked

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}