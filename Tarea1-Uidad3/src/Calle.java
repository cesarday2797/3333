
import library.espacio.espaciofisico.Lugar;
import library.espacio.espaciofisico.Objeto;
import library.espacio.espaciofisico.Posicion;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

public class Calle extends JDialog{
    private Lugar miLugar = new Lugar("Mi lugar");
    private Random random = new Random();
    private JPanel calleP;
    private JPanel imagenP;
    private JPanel botonesP;
    private JButton cancelButton;
    private JButton abbBoton;
    private JButton saveBoton;
    private JButton okButton;
    private JButton abrirBoton;

    public Calle(){
        setContentPane(calleP);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        setContentPane(calleP);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        calleP.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        abbBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Objeto unObjeto = new Objeto("miObjeto" + random.nextInt(10000), new Posicion(random.nextInt(1000),random.nextInt(500)), miLugar);
                miLugar.getLosObjetos().add(unObjeto);
                System.out.print(miLugar);

                Image image = null;
                try {
                    image = ImageIO.read(new File("aOUT.png"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                imagenP.getGraphics().clearRect(0,0, 1000, 500);

                for (int i = 0; i < miLugar.getLosObjetos().size(); i++) {
                    //panelPrincipal.getGraphics().drawRect(miLugar.getLosObjetos().get(i).getLaPosicion().getX(), miLugar.getLosObjetos().get(i).getLaPosicion().getY(), 20, 20);
                    imagenP.getGraphics().drawImage(image,miLugar.getLosObjetos().get(i).getLaPosicion().getX(), miLugar.getLosObjetos().get(i).getLaPosicion().getY(),100,100,null);
                }
            }
        });

        imagenP.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Image image = null;
                try {
                    image = ImageIO.read(new File("aOUT.png"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                imagenP.getGraphics().clearRect(0,0, 1000, 500);

                for (int i = 0; i < miLugar.getLosObjetos().size(); i++) {
                    //panelPrincipal.getGraphics().drawRect(miLugar.getLosObjetos().get(i).getLaPosicion().getX(), miLugar.getLosObjetos().get(i).getLaPosicion().getY(), 20, 20);
                    imagenP.getGraphics().drawImage(image,miLugar.getLosObjetos().get(i).getLaPosicion().getX(), miLugar.getLosObjetos().get(i).getLaPosicion().getY(),100,100,null);
                }
            }
        });
        saveBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream("lugar.dat");
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

                        objectOutputStream.writeObject(miLugar);
                        bufferedOutputStream.flush();

                        objectOutputStream.close();
                        bufferedOutputStream.close();
                        fileOutputStream.close();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        abrirBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                    try {
                        FileInputStream fileInputStream = new FileInputStream(fileChooser.getSelectedFile());
                        //FileInputStream fileInputStream = new FileInputStream("lugar.dat");
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

                        miLugar = (Lugar)objectInputStream.readObject();

                        objectInputStream.close();
                        fileInputStream.close();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    Image image = null;
                    try {
                        image = ImageIO.read(new File("aOUT.png"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    imagenP.getGraphics().clearRect(0,0, 1000, 500);

                    for (int i = 0; i < miLugar.getLosObjetos().size(); i++) {
                        //panelPrincipal.getGraphics().drawRect(miLugar.getLosObjetos().get(i).getLaPosicion().getX(), miLugar.getLosObjetos().get(i).getLaPosicion().getY(), 20, 20);
                        imagenP.getGraphics().drawImage(image,miLugar.getLosObjetos().get(i).getLaPosicion().getX(), miLugar.getLosObjetos().get(i).getLaPosicion().getY(),100,100,null);
                    }
                };
            }
        });
        imagenP.addComponentListener(new ComponentAdapter() {
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Calle dialog = new Calle();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
