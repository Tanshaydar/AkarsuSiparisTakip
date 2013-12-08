/*
 * �Fumera Ar-Ge Yazılım Müh. İml. San. ve Tic. Ltd. Şti. | Copyright 2012-2013
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions 
 * are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright 
 * notice, this list of conditions, and the following disclaimer. 
 * 
 * 2. Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions, and the following disclaimer in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * 3. The name of the author may not be used to endorse or promote products 
 * derived from this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
 * OF TITLE, NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */

package fumera.viewer;

import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Tansel
 */
public class MultiLineTableCellRenderer extends JTextArea 
    implements TableCellRenderer {
    private final List<List<Integer>> rowColHeight = new ArrayList<List<Integer>>();
    
    public MultiLineTableCellRenderer() {
      setLineWrap(true);
      setWrapStyleWord(true);
      setOpaque(true);
    }
    
    @Override
    public Component getTableCellRendererComponent(
        JTable table, Object value, boolean isSelected, boolean hasFocus,
        int row, int column) {
      if (isSelected) {
        setForeground(table.getSelectionForeground());
        setBackground(table.getSelectionBackground());
      } else {
        setForeground(table.getForeground());
        setBackground(table.getBackground());
      }
      setFont(table.getFont());
      if (hasFocus) {
        setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        if (table.isCellEditable(row, column)) {
          setForeground(UIManager.getColor("Table.focusCellForeground"));
          setBackground(UIManager.getColor("Table.focusCellBackground"));
        }
      } else {
        setBorder(new EmptyBorder(1, 2, 1, 2));
      }
      if (value != null) {
        setText(value.toString());
      } else {
        setText("");
      }
      adjustRowHeight(table, row, column);
      return this;
    }
    
    /**
     * Calculate the new preferred height for a given row, and sets the height on the table.
     */
    private void adjustRowHeight(JTable table, int row, int column) {
      //The trick to get this to work properly is to set the width of the column to the 
      //textarea. The reason for this is that getPreferredSize(), without a width tries 
      //to place all the text in one line. By setting the size with the with of the column, 
      //getPreferredSize() returnes the proper height which the row should have in
      //order to make room for the text.
      int cWidth = table.getTableHeader().getColumnModel().getColumn(column).getWidth();
      setSize(new Dimension(cWidth, 1000));
      int prefH = getPreferredSize().height;
      while (rowColHeight.size() <= row) {
        rowColHeight.add(new ArrayList<Integer>(column));
      }
      List<Integer> colHeights = rowColHeight.get(row);
      while (colHeights.size() <= column) {
        colHeights.add(0);
      }
      colHeights.set(column, prefH);
      int maxH = prefH;
      for (Integer colHeight : colHeights) {
        if (colHeight > maxH) {
          maxH = colHeight;
        }
      }
      if (table.getRowHeight(row) != maxH) {
        table.setRowHeight(row, maxH);
      }
    }
  }