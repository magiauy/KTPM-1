package GUI.FromRegister;

import javax.swing.*;
import javax.swing.border.Border;

import BUS.AccountBUS;
import DAO.AcountDAO;
import DTO.Acount;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Login extends JFrame {
    JLabel userLabel = new JLabel("username:");
    JLabel passwordLabel = new JLabel("PASSWORD:");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");

    JCheckBox showPassword = new JCheckBox("Show Password");
    JLabel title = new JLabel("Hello Again!");
    JLabel title2 = new JLabel("Welcome Back");

    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));
        JPanel imagePanel = new JPanel(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon("src\\main\\java\\GUI\\FromRegister\\imgs\\anh1.png");
        Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        JPanel registerPanel = new JPanel(null);

        registerPanel.setPreferredSize(new Dimension(400, 400));

        Font boldFont = new Font(title.getFont().getName(), Font.BOLD, 18);
        title.setFont(boldFont);
        title.setBounds(50, 30, 150, 30);
        title2.setBounds(50, 46, 100, 30);

        userLabel.setBounds(50, 100, 100, 30);
        passwordLabel.setBounds(50, 150, 100, 30);
        userTextField.setBounds(150, 100, 200, 30);
        passwordField.setBounds(150, 150, 200, 30);
        showPassword.setBounds(145, 180, 150, 30);
        loginButton.setBounds(50, 210, 100, 30);
        resetButton.setBounds(200, 210, 100, 30);

        registerPanel.add(title);
        registerPanel.add(title2);

        registerPanel.add(userLabel);
        registerPanel.add(passwordLabel);
        registerPanel.add(userTextField);
        registerPanel.add(passwordField);
        registerPanel.add(showPassword);
        registerPanel.add(loginButton);
        registerPanel.add(resetButton);

        add(imagePanel);
        add(registerPanel);

        pack();
        setLocationRelativeTo(null);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                String password = new String(passwordField.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "Vui lòng nhập email và password!");
                } else {
                    AccountBUS accountBUS = new AccountBUS();
                    boolean loginResult = accountBUS.checkLogin(username, password);
                    if (loginResult) {
                        JOptionPane.showMessageDialog(Login.this, "Đăng nhập thành công!");
                   
                    } else {
                        JOptionPane.showMessageDialog(Login.this,
                                "Đăng nhập thất bại! Vui lòng kiểm tra lại thông tin.");
                    }
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userTextField.setText("");
                passwordField.setText("");
            }
        });
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }

}
