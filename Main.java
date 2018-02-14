import net.infordata.em.crt5250.XI5250Crt;
import net.infordata.em.crt5250.XI5250CrtFrame;
import net.infordata.em.crt5250.XI5250Field;
import net.infordata.em.tn5250.XI5250Emulator;
import net.infordata.em.tn5250.XI5250EmulatorMemento;

import java.util.List;

public class Main {
    public static void main(String[] argv) {
        XI5250Emulator em  = new XI5250Emulator();
        em.setHost("rikas.rikascom.net");
        em.setTerminalType("IBM-3477-FC");

        try {
            em.setActive(true);

            if (em.isActive()){
                System.out.println("System connected");
            }

            Thread.sleep(5000);

            exploreFields(em);

            takeSnapShot(em);

            System.out.println(getScreenAsString(em));

            em.setActive(false);
            if (!em.isActive()){
                System.out.println("System disconnected");

            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void takeSnapShot(XI5250Emulator emu) {
        XI5250Crt clone = emu.getStaticClone();
        String title = "Snap-shot " + emu.getHost();
        XI5250CrtFrame frm = new XI5250CrtFrame(title, clone);
        frm.setBounds(0, 0, 728, 512);
        frm.centerOnScreen();
        frm.setVisible(true);
    }

    private static String getScreenAsString(XI5250Emulator emu){
        int height = emu.getCrtSize().height;
        int width = emu.getCrtSize().width;
        String screen = "";

        for (int i=0;i<height;i++){
            screen += emu.getString(0,i,width);
            screen += "\n";
        }

        return screen;
    }

    public static void exploreFields(XI5250Emulator emu){
        List<XI5250Field> fields = emu.getFields();
        for (int i=0;i<fields.size();i++){
            if (!fields.get(i).isBypassField()){

                int pos = fields.get(i).getCol();
                int posIni = 0;

                if (pos > (emu.getCrtSize().width/2)){
                    posIni = pos - (emu.getCrtSize().width/2);
                }
                System.out.println("field " + i +
                        " str at left = " + emu.getString(posIni,fields.get(i).getRow(),(pos-posIni)));
            }
        }
    }


}
