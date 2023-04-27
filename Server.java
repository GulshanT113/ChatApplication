
//import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;
public class Server extends JFrame {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    private JLabel heading = new JLabel("Server Area");
    private JTextArea msgArea = new JTextArea();
    private JTextField msgInput = new JTextField();
    private Font font = new Font("Roboto", Font.PLAIN, 20);
    public Server(){
        try{
            server = new ServerSocket(7778);
            System.out.println("server is ready to accept connection..........");
            System.out.println("waiting..............");
            socket = server.accept();
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
//            startWriting();
            createGUI();
            handleEvent();
            startReading();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        System.out.println("this is server side view this is going to start now.........");
        new Server();
    }
    private void handleEvent() {

        msgInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == 10){
                    String content = msgInput.getText();
                    msgArea.append("Me : " + content + "\n");
                    out.println(content);
                    out.flush();
                    msgInput.setText("");
                    msgInput.requestFocus();
                }
            }
        });

    }

    private void createGUI() {
        this.setTitle("Server Messenger");
        this.setSize(500, 600);
        this.setLocationRelativeTo(null); // this one create centre location on box.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        heading.setFont(font);
        msgArea.setFont(font);
        msgInput.setFont(font);

        heading.setHorizontalAlignment(SwingConstants.CENTER);
//        heading.setBorder(BorderFactory.createEmptyBorder(22, 20, 22, 20));
//        heading.setIcon(new ImageIcon("gulshan.gpg"));
        msgInput.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout(new BorderLayout());

        this.add(heading, BorderLayout.NORTH);
        this.add(msgArea, BorderLayout.CENTER);
        this.add(msgInput, BorderLayout.SOUTH);

        this.setVisible(true);
    }
//    public void startWriting() {
//        Runnable r1 = ()->{
//            System.out.println("writer starts from here......");
//            try {
//                while (!socket.isClosed()) {
//                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
//                    String content = br1.readLine(); // this thing comes from console.
//                    out.println(content);
//                    out.flush(); // in such case if data is not going on that condition this method helps to send the data forcefully.
//                    if(content.equals("exit")) {
//                        System.out.println("client terminated the conversation from his on side on writing mode.......");
//                        socket.close();
//                        break;
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        };
//
//        new Thread(r1).start();
//    }

    public void startReading() {
        Runnable r2 = ()->{
            System.out.println("reader starts from here......");
            try{
                while(!socket.isClosed()){
                    String msg = br.readLine();
                    if(msg.equals("exit")) {
                        System.out.println("client terminated the conversation from his on side on reading mode.......");
                        JOptionPane.showMessageDialog(this, "chat terminated due to client side");
                        msgInput.setEnabled(false);
                        socket.close();
                        break;
                    }
//                    System.out.println("Client : " + msg);
                    msgArea.append("Client : " + msg + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        new Thread(r2).start();
    }
}