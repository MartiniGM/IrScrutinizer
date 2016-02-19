/*
Copyright (C) 2013, 2015 Bengt Martensson.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3 of the License, or (at
your option) any later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License along with
this program. If not, see http://www.gnu.org/licenses/.
*/

package org.harctoolbox.irscrutinizer.importer;

import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JFileChooser;
import org.harctoolbox.IrpMaster.IrpMasterException;
import org.harctoolbox.girr.RemoteSet;
import org.harctoolbox.guicomponents.CopyClipboardText;
import org.harctoolbox.guicomponents.GuiUtils;
import org.harctoolbox.guicomponents.SelectFile;
import org.harctoolbox.irscrutinizer.Props;

/**
 *
 * @param <T>
 */
public class FileImporterBean<T extends IFileImporter & IImporter>  extends javax.swing.JPanel {
    private transient T importer;
    private GuiUtils guiUtils;
    private transient Props properties;

   public FileImporterBean(GuiUtils guiUtils, Props properties, T importer) {
        this.guiUtils = guiUtils;
        this.importer = importer;
        this.properties = properties;
        initComponents();
        boolean sane = isSane();
        setEnabled(sane);
    }

    /**
     * Creates new form FileImporterBean
     */
    public FileImporterBean() {
        initComponents();
    }

    private boolean isSane() {
        if (guiUtils == null) {
            System.err.println("guiUtils is not setup in FileImporterBean");
            return false;
        }

        if (importer == null) {
            guiUtils.error("importer is null in FileImporterBean");
            return false;
        }

        if (properties == null) {
            guiUtils.error("properties is null in FileImporterBean");
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private RemoteSet getRemoteSet(T importer) {
        return IRemoteSetImporter.class.isInstance(importer) ? ((IRemoteSetImporter)importer).getRemoteSet()
                : IModulatedIrSequenceImporter.class.isInstance(importer)
                ? new RemoteSet(importer.getFormatName() + " import", properties.getCreatingUser(), ((IModulatedIrSequenceImporter) importer).getModulatedIrSequence().toIrSignal(), "unnamed", null, "unknown")
                : null;
    }

    @SuppressWarnings("unchecked")
    private RemoteSet remoteSetImporterLoadFile() throws IOException, ParseException, IrpMasterException {
        Cursor oldCursor = getCursor();
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (IReaderImporter.class.isInstance(importer))
                ((IReaderImporter)importer).load(filenameTextField.getText().trim(), properties.getImportOpensZipFiles(), properties.getImportCharsetName());
            else
                importer.possiblyZipLoad(new File(filenameTextField.getText().trim()), properties.getImportCharsetName());
            return getRemoteSet(importer);
        } finally {
            setCursor(oldCursor);
        }
    }

    @SuppressWarnings("unchecked")
    private RemoteSet remoteSetImporterLoadClipboard() throws IOException, ParseException, IrpMasterException {
        Cursor oldCursor = getCursor();
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            String payload = (new CopyClipboardText(null)).fromClipboard();
            if (payload == null) {
                guiUtils.error("No usable clipboard content");
                return null;
            }
            ((IReaderImporter)importer).load(payload, "<clipboard>", properties.getImportCharsetName());
            return getRemoteSet(importer);
        } finally {
            setCursor(oldCursor);
        }
    }

    @Override
    public final void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        treeImporter.setEnabled(enabled);
        selectButton.setEnabled(enabled);
        loadFileButton.setEnabled(enabled);
        loadClipboardButton.setEnabled(enabled && IReaderImporter.class.isInstance(importer));
        editBrowseButton.setEnabled(enabled);
        if (!IReaderImporter.class.isInstance(importer)) {
            loadClipboardButton.setVisible(false);
            loadFileButton.setText("Load File");
            fileUrlLabel.setText("File");
        }
        loadHardwareButton.setVisible(false); // Better to nuke it?
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        copyPastePopupMenu1 = new org.harctoolbox.guicomponents.CopyPastePopupMenu(true);
        treeImporter = new org.harctoolbox.irscrutinizer.importer.TreeImporter(guiUtils);
        filenameTextField = new javax.swing.JTextField();
        fileUrlLabel = new javax.swing.JLabel();
        selectButton = new javax.swing.JButton();
        loadFileButton = new javax.swing.JButton();
        loadClipboardButton = new javax.swing.JButton();
        editBrowseButton = new javax.swing.JButton();
        loadHardwareButton = new javax.swing.JButton();

        filenameTextField.setComponentPopupMenu(copyPastePopupMenu1);

        fileUrlLabel.setText("File/URL:");

        selectButton.setText("...");
        selectButton.setToolTipText("Allows for selecting a local file by the use of a file selector. Does not load it, however. For this, use the \"Load File/URL\" button\".");
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });

        loadFileButton.setText("Load File/URL");
        loadFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadFileButtonActionPerformed(evt);
            }
        });

        loadClipboardButton.setText("Load from clipboard");
        loadClipboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadClipboardButtonActionPerformed(evt);
            }
        });

        editBrowseButton.setText("Edit/View");
        editBrowseButton.setToolTipText("Allows for viewing or editing of the selected file/URL.");
        editBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBrowseButtonActionPerformed(evt);
            }
        });

        loadHardwareButton.setText("Load from Hardware");
        loadHardwareButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadHardwareButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(treeImporter, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(loadFileButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadClipboardButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadHardwareButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editBrowseButton)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fileUrlLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filenameTextField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(selectButton)))))
                .addGap(0, 0, 0))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {editBrowseButton, loadClipboardButton, loadFileButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileUrlLabel)
                    .addComponent(filenameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectButton))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadFileButton)
                    .addComponent(loadClipboardButton)
                    .addComponent(editBrowseButton)
                    .addComponent(loadHardwareButton))
                .addGap(6, 6, 6)
                .addComponent(treeImporter, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {editBrowseButton, loadClipboardButton, loadFileButton});

    }// </editor-fold>//GEN-END:initComponents

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        if (!isSane())
            return;

        int selectionType = importer.canImportDirectories() ? JFileChooser.FILES_AND_DIRECTORIES : JFileChooser.FILES_ONLY;
        String[][] filetypes = importer.getFileExtensions(properties.getImportOpensZipFiles());

        File importFile = SelectFile.selectFile(this, "Select " + importer.getFormatName() + " file to import",
                properties.getDefaultImportDir(), false, false, selectionType, filetypes);
        if (importFile != null) {
            filenameTextField.setText(importFile.getPath());
            // Tell the user that the data in the tree importer is obsolete.
            treeImporter.clear();
            try {
                properties.setDefaultImportDir(importFile.getParentFile().getCanonicalPath());
            } catch (IOException ex) {
                // we may ignore this
            }
        }
    }//GEN-LAST:event_selectButtonActionPerformed

    private void loadFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadFileButtonActionPerformed
        // If filename/URL is empty, fire up files selector instead of complaining
        if (filenameTextField.getText().isEmpty())
            selectButtonActionPerformed(evt);

        // if it still is empty, bail out
        if (filenameTextField.getText().isEmpty())
            return;

        Cursor oldCursor = getCursor();
        repaint();
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
            }
            RemoteSet remoteSet = remoteSetImporterLoadFile();
            treeImporter.setRemoteSet(remoteSet);
        } catch (IOException | ParseException | IrpMasterException | UnsupportedOperationException ex) {
            treeImporter.clear();
            guiUtils.error(ex);
        } finally {
            setCursor(oldCursor);
        }
    }//GEN-LAST:event_loadFileButtonActionPerformed

    private void editBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBrowseButtonActionPerformed
        String filename = filenameTextField.getText();
        if (filename.isEmpty()) {
            guiUtils.error("File/URL empty");
            return;
        }
        guiUtils.browseOrEdit(filename);
    }//GEN-LAST:event_editBrowseButtonActionPerformed

    private void loadClipboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadClipboardButtonActionPerformed
        try {
            RemoteSet remoteSet = remoteSetImporterLoadClipboard();
            treeImporter.setRemoteSet(remoteSet);
        } catch (IOException | IrpMasterException | ParseException ex) {
            guiUtils.error(ex);
        }
    }//GEN-LAST:event_loadClipboardButtonActionPerformed

    private void loadHardwareButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadHardwareButtonActionPerformed
        boolean ack = guiUtils.confirm("This may take several minutes and cannot be interrupted. Continue?");
        if (!ack)
            return;
        getParent().repaint(); // necessary to have the cursor change take effect

    }//GEN-LAST:event_loadHardwareButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.harctoolbox.guicomponents.CopyPastePopupMenu copyPastePopupMenu1;
    private javax.swing.JButton editBrowseButton;
    private javax.swing.JLabel fileUrlLabel;
    private javax.swing.JTextField filenameTextField;
    private javax.swing.JButton loadClipboardButton;
    private javax.swing.JButton loadFileButton;
    private javax.swing.JButton loadHardwareButton;
    private javax.swing.JButton selectButton;
    private org.harctoolbox.irscrutinizer.importer.TreeImporter treeImporter;
    // End of variables declaration//GEN-END:variables
}
