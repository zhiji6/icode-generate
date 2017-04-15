package ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import generate.Generate;
import generate.IDataBase;
import generate.impl.GenerateDataBase;
import org.apache.commons.lang3.StringUtils;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
/**
 * @author alex-jiayu
 * @create 2017-04-14 17:55
 **/
public class Frame extends JFrame{
    private static final long serialVersionUID = 6636098005177941822L;
    private JPanel contentPane;
    private JTextField txt_username;
    private JTextField txt_password;
    private JTextField txt_tableprefix;
    private JTextField txt_tableName;
    private final String[] drivers = { "com.mysql.jdbc.Driver", "oracle.jdbc.dirver.OracleDriver" };
    private JTextField txt_url;

    private IDataBase iDataBase;
    private Generate generate;

    /** UIManager中UI字体相关的key */
    public static String[] DEFAULT_FONT = new String[] { "Table.font", "TableHeader.font", "CheckBox.font",
            "Tree.font", "Viewport.font", "ProgressBar.font", "RadioButtonMenuItem.font", "ToolBar.font",
            "ColorChooser.font", "ToggleButton.font", "Panel.font", "TextArea.font", "Menu.font", "TableHeader.font",
            "TextField.font", "OptionPane.font", "MenuBar.font", "Button.font", "Label.font", "PasswordField.font",
            "ScrollPane.font", "MenuItem.font", "ToolTip.font", "List.font", "EditorPane.font", "Table.font",
            "TabbedPane.font", "RadioButton.font", "CheckBoxMenuItem.font", "TextPane.font", "PopupMenu.font",
            "TitledBorder.font", "ComboBox.font" };

    private final Object[] options = {" 确定 "," 取消 "};

    private JTextField txt_author;
    private JTextField txt_contact;
    private JTextField txt_package;
    private JTextField txt_outdir;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
                    UIManager.put("RootPane.setupButtonVisible", false);
                    //设置本属性将改变窗口边框样式定义
                    BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
                    org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
                    // 调整默认字体
                    for (int i = 0; i < DEFAULT_FONT.length; i++) {
                        UIManager.put(DEFAULT_FONT[i], new Font("微软雅黑", Font.PLAIN, 13));
                    }
                    Frame frame = new Frame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Frame() {
        init();
    }

    /**
     * 初始化布局
     */
    void init(){

        setTitle("icode-generate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 377, 490);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("数据库连接：");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBounds(10, 35, 90, 15);
        contentPane.add(label);

        final JComboBox comboBox_driver = new JComboBox(drivers);
        comboBox_driver.setBounds(110, 30, 205, 25);
        contentPane.add(comboBox_driver);

        JLabel lblUsername = new JLabel("用户名：");
        lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUsername.setVerticalAlignment(SwingConstants.BOTTOM);
        lblUsername.setBounds(10, 75, 90, 15);
        contentPane.add(lblUsername);

        txt_username = new JTextField();
        txt_username.setText("root");
        txt_username.setBounds(110, 70, 205, 25);
        contentPane.add(txt_username);
        txt_username.setColumns(10);

        JLabel lblPassword = new JLabel("密码：");
        lblPassword.setVerticalAlignment(SwingConstants.BOTTOM);
        lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPassword.setBounds(10, 115, 90, 15);
        contentPane.add(lblPassword);

        txt_password = new JTextField();
        txt_password.setText("123456");
        txt_password.setColumns(10);
        txt_password.setBounds(110, 110, 205, 25);
        contentPane.add(txt_password);

        JButton btn_test = new JButton("测试连接");
        btn_test.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        btn_test.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validation()) {
                    String classDriver = comboBox_driver.getSelectedItem().toString();
                    String url = txt_url.getText();
                    String username = txt_username.getText();
                    String password = txt_password.getText();
                    iDataBase = new GenerateDataBase(classDriver, url, username, password);
                    Connection conn = iDataBase.getConnJDBC();
                    if (null != conn) {
                        showInfo("连接成功！");
                    } else {
                        showError("数据库连接失败，请检查后重试！");
                    }
                }
            }
        });
        btn_test.setBounds(55, 415, 95, 25);
        contentPane.add(btn_test);

        JButton btn_gener = new JButton("生成代码");
        btn_gener.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validation()) {
                    String classDriver = comboBox_driver.getSelectedItem().toString();
                    String url = txt_url.getText();
                    String username = txt_username.getText();
                    String password = txt_password.getText();
                    String classPackage = txt_package.getText();
                    String author = Generate.AUTHOR;
                    //String author = txt_author.getText();
                    String outPath = txt_outdir.getText();
                    //String prefix = txt_tableprefix.getText();
                    String contact = Generate.CONTACT;
                    //String contact = txt_contact.getText();
                    //Generate.PREFIX = prefix;
                    String tableName = txt_tableName.getText();

                    String schema = url.substring(url.lastIndexOf("/") + 1);
                    if (StringUtils.isBlank(schema))
                        showError("请检查数据库url是否完整");
                    else{
                        generate = new Generate(classDriver, url, username, password , tableName ,schema);
                        boolean flag = generate.generate(classPackage, author, contact, outPath);
                        if (flag) {
                            int response = JOptionPane.showOptionDialog(Frame.this, "代码已经生成，是否打开输出目录？", "确认", JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                            if (response == 0) {
                                //打开文件夹
                                try {
                                    Runtime.getRuntime().exec("cmd.exe /c start " + outPath);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        });
        btn_gener.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue));
        btn_gener.setBounds(220, 415, 95, 25);
        contentPane.add(btn_gener);

        /*txt_tableprefix = new JTextField();
        txt_tableprefix.setColumns(10);
        txt_tableprefix.setBounds(110, 225, 205, 25);
        contentPane.add(txt_tableprefix);
        JLabel label_1 = new JLabel("数据表前缀：");
        label_1.setVerticalAlignment(SwingConstants.BOTTOM);
        label_1.setHorizontalAlignment(SwingConstants.RIGHT);
        label_1.setBounds(10, 230, 90, 15);
        contentPane.add(label_1);*/

        txt_tableName = new JTextField();
        txt_tableName.setColumns(10);
        txt_tableName.setBounds(110, 225, 205, 25);
        contentPane.add(txt_tableName);

        JLabel label_1 = new JLabel("数据库表：");
        label_1.setVerticalAlignment(SwingConstants.BOTTOM);
        label_1.setHorizontalAlignment(SwingConstants.RIGHT);
        label_1.setBounds(10, 230, 90, 15);
        contentPane.add(label_1);

        JLabel lblUrl = new JLabel("数据库URL：");
        lblUrl.setVerticalAlignment(SwingConstants.BOTTOM);
        lblUrl.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUrl.setBounds(10, 155, 90, 15);
        contentPane.add(lblUrl);

        txt_url = new JTextField();
        txt_url.setText("jdbc:mysql://127.0.0.1:3306/");
        txt_url.setColumns(10);
        txt_url.setBounds(110, 150, 205, 25);
        contentPane.add(txt_url);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 270, 350, 2);
        contentPane.add(separator);

        JLabel label_2 = new JLabel("作者：");
        label_2.setVerticalAlignment(SwingConstants.BOTTOM);
        label_2.setHorizontalAlignment(SwingConstants.RIGHT);
        label_2.setBounds(10, 299, 90, 15);
        contentPane.add(label_2);

        JLabel label_a = new JLabel("alex / jiayu");
        label_a.setBounds(110, 294, 205, 25);
        contentPane.add(label_a);


        JLabel label_c = new JLabel("alexdennis.lam@gmail.com");
        label_c.setBounds(110, 334, 205, 25);
        contentPane.add(label_c);

        JLabel label_3 = new JLabel("联系方式：");
        label_3.setVerticalAlignment(SwingConstants.BOTTOM);
        label_3.setHorizontalAlignment(SwingConstants.RIGHT);
        label_3.setBounds(10, 339, 90, 15);
        contentPane.add(label_3);

        txt_package = new JTextField();
        txt_package.setText("com.icode");
        txt_package.setColumns(10);
        txt_package.setBounds(110, 190, 205, 25);
        contentPane.add(txt_package);

        JLabel label_4 = new JLabel("生成包前缀：");
        label_4.setVerticalAlignment(SwingConstants.BOTTOM);
        label_4.setHorizontalAlignment(SwingConstants.RIGHT);
        label_4.setBounds(10, 195, 90, 15);
        contentPane.add(label_4);

        JLabel label_5 = new JLabel("生成目录：");
        label_5.setVerticalAlignment(SwingConstants.BOTTOM);
        label_5.setHorizontalAlignment(SwingConstants.RIGHT);
        label_5.setBounds(10, 374, 90, 15);
        contentPane.add(label_5);

        txt_outdir = new JTextField();
        txt_outdir.setText("D:/exemple");
        txt_outdir.setColumns(10);
        txt_outdir.setBounds(110, 369, 205, 25);
        contentPane.add(txt_outdir);

        setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * 验证是否为空
     * @return
     */
    public boolean validation(){
        if(isEmpty(txt_username)){
            showWarning("用户名不能为空！");
            txt_username.requestFocus();
            return false;
        }
        /*if(isEmpty(txt_password)){
            showWarning("password不能为空！");
            txt_password.requestFocus();
            return false;
        }*/
        if(isEmpty(txt_url)){
            showWarning("数据库URL不能为空！");
            txt_url.requestFocus();
            return false;
        }
        if(isEmpty(txt_package)){
            showWarning("包前缀不能为空！");
            txt_package.requestFocus();
            return false;
        }
        if(isEmpty(txt_outdir)){
            showWarning("生成代码目录不能为空！");
            txt_outdir.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 判断输入框是否为空
     * @param component
     * @return
     */
    public boolean isEmpty(JTextComponent component){
        String content = component.getText();
        return null == content || content.equals("");
    }

    /**
     * 错误提示
     * @param msg
     */
    public void showError(String msg){
        JOptionPane.showMessageDialog(null, msg, "错误", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * 警告提示
     * @param msg
     */
    public void showWarning(String msg){
        JOptionPane.showMessageDialog(null, msg, "警告", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * 信息提示
     * @param msg
     */
    public void showInfo(String msg){
        JOptionPane.showMessageDialog(null, msg, "信息", JOptionPane.INFORMATION_MESSAGE);
    }
}