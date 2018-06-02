package Game2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

class HgihScore extends JDialog
{
    public final JPanel contentPanel = new JPanel();
    public JTextField scoreTitleField;
    public JTextArea userScoreArea = new JTextArea();
    /**
     * Launch the application.
     */

    /**
     * Create the dialog.
     */
    public HgihScore() {
       
        setBounds(100, 100, 323, 652);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            userScoreArea.setEditable(false);
            userScoreArea.setText("1 \u9648\u6D69\u7136  5463");
            userScoreArea.setFont(new Font("¿¬Ìå", Font.PLAIN, 25));
            userScoreArea.setBounds(48, 63, 219, 468);
            contentPanel.add(userScoreArea);
        }
        {
            scoreTitleField = new JTextField();
            scoreTitleField.setEditable(false);
            scoreTitleField.setBounds(74, 10, 166, 42);
            scoreTitleField.setHorizontalAlignment(SwingConstants.CENTER);
            scoreTitleField.setFont(new Font("¿¬Ìå", Font.PLAIN, 31));
            scoreTitleField.setText("\u9AD8\u5206\u9F99\u864E\u699C");
            contentPanel.add(scoreTitleField);
            scoreTitleField.setColumns(10);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("\u77BB\u4EF0\u4E86");
                okButton.setActionCommand("");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("\u771F\u53EF\u6015");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }
}
