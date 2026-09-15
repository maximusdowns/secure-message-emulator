package com.maxdowns.securemessage;

import javax.swing.*;
import java.awt.*;

public class SecureMessageFrame extends JFrame {
    private final MessageService messageService;
    private final JTextField senderField;
    private final JTextField recipientField;
    private final JTextArea messageArea;

    public SecureMessageFrame(MessageService messageService) {
         this.messageService = messageService;

        senderField = new JTextField();
        recipientField = new JTextField();
        messageArea = new JTextArea();

        initializeUi();
    }

    private void initializeUi() {
        setTitle("Secure Message Emulator");
        setSize(600,400);
        setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Compose Secure Message");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets (5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel senderLabel = new JLabel("Sender");
        JLabel recipientLabel = new JLabel("Recipient:");
        JLabel messageLabel = new JLabel("Message:");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formPanel.add(senderLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(senderField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(recipientLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(recipientField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        formPanel.add(messageLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        formPanel.add(messageArea, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton sendButton = new JButton("Send Message");

        sendButton.addActionListener(event -> sendMessage());

        buttonPanel.add(sendButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void sendMessage() {
        String sender = senderField.getText();
        String recipient = recipientField.getText();
        String body = messageArea.getText();

        // Domain object determines what is valid;
        // UI determines how validation errors are presented to the user.
        try {
            Message message = messageService.createDraft(sender, recipient, body); //DI
            messageService.send(message);

            JOptionPane.showMessageDialog(
                    this,
                    "Message sent successfully.\nStatus: " + message.getStatus(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            senderField.setText("");
            recipientField.setText("");
            messageArea.setText("");

            senderField.requestFocusInWindow();
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    exception.getMessage(),
                    "Invalid Message",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {  // Lambda expression - here is a block of code I want you to execute later
            MessageRepository repository = new InMemoryMessageRepository();
            MessageService service = new MessageService(repository);  //use the dependency injection here instead of the click event

            SecureMessageFrame frame = new SecureMessageFrame(service);
            frame.setVisible(true);
        });
    }
}
