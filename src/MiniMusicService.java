import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MiniMusicService implements Service {

    MyDrawPanel mypanel;
    public JPanel getGuiPanel(){
        JPanel mainPanel = new JPanel();
        mypanel = new MyDrawPanel();
        JButton playItButtton = new JButton("Play It");
        playItButtton.addActionListener(new PlayItListener());
        mainPanel.add(mypanel);
        mainPanel.add(playItButtton);
        return mainPanel;
    }

    public class PlayItListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                Sequencer sequencer = MidiSystem.getSequencer();
                sequencer.open();
                sequencer.addControllerEventListener(mypanel, new int[] {127});
                Sequence seq = new Sequence(Sequence.PPQ, 4);
                Track track = seq.createTrack();


                for (int i=0;i<100;i+=4){
                    int rNum = (int)((Math.random()*50) +1);
                    if(rNum < 38) { // Now only do it for num < 38 (75% of time)
                        track.add(makeEvent(144, 1, rNum, 100, i));
                        track.add(makeEvent(176, 1, 127, 0, i));
                        track.add(makeEvent(128, 1, rNum, 100, i + 2));
                    }

                }

                sequencer.setSequence(seq);
                sequencer.start();
                sequencer.setTempoInBPM(120);
            }catch (Exception ex){ex.printStackTrace();}

        }


    }
    public MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){
        MidiEvent event = null;
        try{
            ShortMessage a= new ShortMessage();
            a.setMessage(comd,chan,one,two);
            event = new MidiEvent(a, tick);

        }catch (Exception e){}

        return  event;
    }

    public  class  MyDrawPanel extends JPanel implements ControllerEventListener{
        boolean msg = false;

        public void controlChange(ShortMessage event){
            msg = true;
            repaint();
        }
        public void paintComponent(Graphics g){
            if(msg){
                Graphics2D g2 = (Graphics2D) g;

                int r =(int)(Math.random()*250);
                int gr =(int)(Math.random()*250);
                int b =(int)(Math.random()*250);

                g.setColor(new Color(r, gr,b));
                int ht = (int)((Math.random()*120) + 10);
                int width = (int)((Math.random()*120) + 10);

                int x = (int)((Math.random()*40) + 10);
                int y = (int)((Math.random()*40) + 10);

                g.fillRect(x,y,ht,width);
                msg = false;

            }
        }

    }



}
