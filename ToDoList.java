import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TodoListApp extends JFrame {

    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JTextField taskInputField;
    private JButton addButton;

    public TodoListApp() {
        setTitle("To-Do List");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.BorderLayout());

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        add(new JScrollPane(taskList), java.awt.BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        taskInputField = new JTextField(20);
        addButton = new JButton("Add Task");
        inputPanel.add(taskInputField);
        inputPanel.add(addButton);
        add(inputPanel, java.awt.BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newTask = taskInputField.getText().trim();
                if (!newTask.isEmpty()) {
                    listModel.addElement(newTask);
                    taskInputField.setText("");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TodoListApp().setVisible(true);
        });
    }
}