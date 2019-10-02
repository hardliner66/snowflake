package snowflake.components.settings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import snowflake.App;
import snowflake.AppConstants;
import snowflake.common.Settings;
import snowflake.components.newsession.SavedSessionTree;
import snowflake.components.newsession.SessionFolder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SettingsPanel extends JPanel {
    private JCheckBox chkConfirmBeforeDelete, chkConfirmBeforeMoveOrCopy, chkShowHiddenFilesByDefault,
            chkPromptForSudo, chkDirectoryCache, chkShowPathBar,
            chkConfirmBeforeTerminalClosing, chkUseDarkThemeForTerminal, chkShowMessagePrompt;
    private JComboBox<String> cmbDefaultOpenAction;
    private JComboBox<String> cmbTermType;
    private JComboBox<Integer> cmbNumberOfSimultaneousConnection;
    private Settings settings;
    private JDialog dlg;
    private JFrame frame;

    public void load(Settings settings) {
        this.settings = settings;
        chkConfirmBeforeDelete.setSelected(settings.isConfirmBeforeDelete());
        chkConfirmBeforeMoveOrCopy.setSelected(settings.isConfirmBeforeMoveOrCopy());
        chkShowHiddenFilesByDefault.setSelected(settings.isShowHiddenFilesByDefault());
        chkPromptForSudo.setSelected(settings.isPromptForSudo());
        chkDirectoryCache.setSelected(settings.isDirectoryCache());
        chkShowPathBar.setSelected(settings.isShowPathBar());
        chkConfirmBeforeTerminalClosing.setSelected(settings.isConfirmBeforeTerminalClosing());
        chkUseDarkThemeForTerminal.setSelected(settings.isUseDarkThemeForTerminal());
        chkShowMessagePrompt.setSelected(settings.isShowMessagePrompt());

        cmbDefaultOpenAction.setSelectedIndex(settings.getDefaultOpenAction());
        cmbNumberOfSimultaneousConnection.setSelectedItem(settings.getNumberOfSimultaneousConnection());
        cmbTermType.setSelectedItem(settings.getTerminalType());
    }

    public void applySettings() {
        settings.setConfirmBeforeDelete(chkConfirmBeforeDelete.isSelected());
        settings.setConfirmBeforeMoveOrCopy(chkConfirmBeforeMoveOrCopy.isSelected());
        settings.setShowHiddenFilesByDefault(chkShowHiddenFilesByDefault.isSelected());
        settings.setPromptForSudo(chkPromptForSudo.isSelected());
        settings.setDirectoryCache(chkDirectoryCache.isSelected());
        settings.setShowPathBar(chkShowPathBar.isSelected());
        settings.setConfirmBeforeTerminalClosing(chkConfirmBeforeTerminalClosing.isSelected());
        settings.setUseDarkThemeForTerminal(chkUseDarkThemeForTerminal.isSelected());
        settings.setShowMessagePrompt(chkShowMessagePrompt.isSelected());

        settings.setDefaultOpenAction(cmbDefaultOpenAction.getSelectedIndex());
        settings.setNumberOfSimultaneousConnection((Integer) cmbNumberOfSimultaneousConnection.getSelectedItem());
        settings.setTerminalType((String) cmbTermType.getSelectedItem());
    }

    public SettingsPanel(JFrame frame) {
        super(new BorderLayout());
        this.frame = frame;
        chkConfirmBeforeDelete = new JCheckBox("Confirm before deleting files");
        chkConfirmBeforeMoveOrCopy = new JCheckBox("Confirm before moving or copying files");
        chkShowHiddenFilesByDefault = new JCheckBox("Show hidden files by default");
        chkPromptForSudo = new JCheckBox("Prompt for sudo if operation fails due to permission issues");
        chkDirectoryCache = new JCheckBox("Use directory caching");
        chkShowPathBar = new JCheckBox("Show current folder in path bar style");
        chkConfirmBeforeTerminalClosing = new JCheckBox("Confirm before closing a terminal session");
        chkUseDarkThemeForTerminal = new JCheckBox("Use dark terminal theme");
        chkShowMessagePrompt = new JCheckBox("Show message prompt");

        cmbDefaultOpenAction = new JComboBox<>(new String[]{"Open with default application", "Open with default editor", "Open with internal editor"});
        cmbDefaultOpenAction.setMaximumSize(cmbDefaultOpenAction.getPreferredSize());
        cmbNumberOfSimultaneousConnection = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        cmbNumberOfSimultaneousConnection.setMaximumSize(new Dimension(cmbDefaultOpenAction.getPreferredSize().width * 2, cmbDefaultOpenAction.getPreferredSize().height));
        cmbTermType = new JComboBox<>(new String[]{"vt100", "xterm", "xterm-256color"});
        cmbTermType.setMaximumSize(new Dimension(cmbTermType.getPreferredSize().width * 2, cmbTermType.getPreferredSize().height));


        Box hb1 = Box.createHorizontalBox();
        hb1.add(new JLabel("Default action for double click on files"));
        hb1.add(Box.createHorizontalGlue());
        hb1.add(cmbDefaultOpenAction);

        Box hb2 = Box.createHorizontalBox();
        hb2.add(new JLabel("Number of simultaneous connection for background file transfer"));
        hb2.add(Box.createHorizontalGlue());
        hb2.add(cmbNumberOfSimultaneousConnection);

        Box hb3 = Box.createHorizontalBox();
        hb3.add(new JLabel("Terminal type"));
        hb3.add(Box.createHorizontalGlue());
        hb3.add(cmbTermType);

        Box vbox = Box.createVerticalBox();
        chkConfirmBeforeDelete.setAlignmentX(Box.LEFT_ALIGNMENT);
        chkConfirmBeforeMoveOrCopy.setAlignmentX(Box.LEFT_ALIGNMENT);
        chkShowHiddenFilesByDefault.setAlignmentX(Box.LEFT_ALIGNMENT);
        chkPromptForSudo.setAlignmentX(Box.LEFT_ALIGNMENT);
        chkDirectoryCache.setAlignmentX(Box.LEFT_ALIGNMENT);
        chkShowPathBar.setAlignmentX(Box.LEFT_ALIGNMENT);
        chkConfirmBeforeTerminalClosing.setAlignmentX(Box.LEFT_ALIGNMENT);
        chkUseDarkThemeForTerminal.setAlignmentX(Box.LEFT_ALIGNMENT);
        chkShowMessagePrompt.setAlignmentX(Box.LEFT_ALIGNMENT);
        hb1.setAlignmentX(Box.LEFT_ALIGNMENT);
        hb2.setAlignmentX(Box.LEFT_ALIGNMENT);
        hb3.setAlignmentX(Box.LEFT_ALIGNMENT);

        vbox.add(chkConfirmBeforeDelete);
        vbox.add(Box.createRigidArea(new Dimension(10, 10)));
        vbox.add(chkConfirmBeforeMoveOrCopy);
        vbox.add(Box.createRigidArea(new Dimension(10, 10)));
        vbox.add(chkShowHiddenFilesByDefault);
        vbox.add(Box.createRigidArea(new Dimension(10, 10)));
        vbox.add(chkPromptForSudo);
        vbox.add(Box.createRigidArea(new Dimension(10, 10)));
        vbox.add(chkDirectoryCache);
        vbox.add(Box.createRigidArea(new Dimension(10, 10)));
        vbox.add(chkShowPathBar);
        vbox.add(Box.createRigidArea(new Dimension(10, 10)));
        vbox.add(chkConfirmBeforeTerminalClosing);
        vbox.add(Box.createRigidArea(new Dimension(10, 10)));
        vbox.add(chkUseDarkThemeForTerminal);
        vbox.add(Box.createRigidArea(new Dimension(10, 10)));
        vbox.add(chkShowMessagePrompt);
        vbox.add(Box.createRigidArea(new Dimension(10, 10)));
        vbox.add(hb1);
        vbox.add(Box.createRigidArea(new Dimension(10, 10)));
        vbox.add(hb2);
        vbox.add(Box.createRigidArea(new Dimension(10, 10)));
        vbox.add(hb3);
        vbox.add(Box.createRigidArea(new Dimension(10, 10)));
        vbox.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(vbox);

        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalGlue());
        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        btnOK.addActionListener(e -> {
            applySettings();
            App.saveSettings();
            dlg.setVisible(false);
        });
        btnCancel.addActionListener(e -> {
            dlg.setVisible(false);
        });
        box.add(btnOK);
        box.add(Box.createHorizontalStrut(10));
        box.add(btnCancel);
        box.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(box, BorderLayout.SOUTH);

        dlg = new JDialog(frame);
        dlg.setTitle("Settings");
        dlg.add(this);
        dlg.setModal(true);
        dlg.setSize(640, 480);
    }


    public void showDialog(Settings settings) {
        load(settings);
        dlg.setLocationRelativeTo(frame);
        dlg.setVisible(true);
    }
}
