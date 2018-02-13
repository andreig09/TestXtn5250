import net.infordata.em.crt5250.XI5250Crt;
import net.infordata.em.crt5250.XI5250CrtFrame;
import net.infordata.em.tn5250.XI5250Emulator;
import net.infordata.em.tn5250.XI5250EmulatorCtrl;

public class Main {
    public static void main(String[] argv) {
        //XITelnet tn = new XITelnet("rikas.rikascom.net");
        XI5250Emulator em  = new XI5250Emulator();
        em.setHost("rikas.rikascom.net");
        em.setTerminalType("IBM-3179-2");
        XI5250EmulatorCtrl emctrl = new XI5250EmulatorCtrl(em);

        try {
            em.setActive(true);

            if (em.isActive()){
                System.out.println("System connected");
            }

            Thread.sleep(10000);

            takeSnapShot(em);

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


}
