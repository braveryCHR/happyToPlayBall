package Game2;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class getYourName extends JDialog
{
    public final JPanel contentPanel = new JPanel();
    public  JTextField nameField;
    public JButton okButton = new JButton("确定");

    /**
     * Create the dialog.
     */
    public getYourName() {
        setTitle("游戏结束");
        setBounds(100, 100, 401, 254);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                }
            });
            okButton.setBounds(136, 155, 102, 39);
            okButton.setFont(new Font("宋体", Font.PLAIN, 26));
            contentPanel.add(okButton);
        }
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                String name = GameStart.getNameDialog.nameField.getText();
                if (name.equals("")) {
                    nameField.setText("姓名不能为空");
                    return;
                } else {
                    String score = GameStart.scorePanel.getText();
                    System.out.println(name + "  " + score);
                    GameStart.userScores.add(new UserScore(name, score));
                    GameStart.userScores.sort(null);
                    GameStart.getNameDialog.setVisible(false);
                }
            }
        });
        JLabel label = new JLabel("请留下您的尊姓大名");
        label.setFont(new Font("宋体", Font.PLAIN, 25));
        label.setBounds(14, 24, 299, 39);
        contentPanel.add(label);
        nameField = new JTextField();
        nameField.setFont(new Font("宋体", Font.PLAIN, 25));
        nameField.setBounds(81, 77, 232, 53);
        contentPanel.add(nameField);
        nameField.setColumns(10);
    }
}
