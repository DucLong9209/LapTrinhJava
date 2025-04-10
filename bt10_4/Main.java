package bt10_4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<User> users = new ArrayList<>();
    private static DefaultListModel<String> userListModel = new DefaultListModel<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ứng dụng Người dùng");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        panel.add(new JLabel("Tên người dùng:"));
        panel.add(usernameField);
        panel.add(new JLabel("Mật khẩu:"));
        panel.add(passwordField);

        JButton registerBtn = new JButton("Đăng ký");
        JButton loginBtn = new JButton("Đăng nhập");
        panel.add(registerBtn);
        panel.add(loginBtn);

        userListModel = new DefaultListModel<>();
        JList<String> userList = new JList<>(userListModel);
        JScrollPane scrollPane = new JScrollPane(userList);

        JPanel bottomPanel = new JPanel();
        JButton exportBtn = new JButton("Xuất XML");
        JButton importBtn = new JButton("Nhập XML");
        bottomPanel.add(exportBtn);
        bottomPanel.add(importBtn);

        registerBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Không được bỏ trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (User u : users) {
                if (u.getUsername().equals(username)) {
                    JOptionPane.showMessageDialog(frame, "Tên người dùng đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String hashed = HashUtil.hashPassword(password);
            users.add(new User(username, hashed));
            userListModel.addElement(username);
            JOptionPane.showMessageDialog(frame, "Đăng ký thành công!", "OK", JOptionPane.INFORMATION_MESSAGE);
        });

        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String hashed = HashUtil.hashPassword(password);

            boolean found = false;
            for (User u : users) {
                if (u.getUsername().equals(username) && u.getHashedPassword().equals(hashed)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                JOptionPane.showMessageDialog(frame, "Đăng nhập thành công!", "OK", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Sai tên hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        exportBtn.addActionListener(e -> {
            XMLUtil.exportToXML(users, "users.xml");
            JOptionPane.showMessageDialog(frame, "Xuất ra users.xml thành công!");
        });

        importBtn.addActionListener(e -> {
            List<User> imported = XMLUtil.importFromXML("users.xml");
            users.clear();
            users.addAll(imported);
            userListModel.clear();
            for (User u : imported) {
                userListModel.addElement(u.getUsername());
            }
            JOptionPane.showMessageDialog(frame, "Nhập từ users.xml thành công!");
        });

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}

