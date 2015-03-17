import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class DrawSimulation extends JPanel {
	
    private Simulation simulation;

	public DrawSimulation(Simulation simulation) {
		this.simulation = simulation;
		this.setPreferredSize(new Dimension(600, 200));
        this.setBackground(Color.WHITE);
	}

	@Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        int i=0;
        
        for(Process p : simulation.getListOfProcesses()){
        	
        	int cylWidth = 10;
            int cylHeightRatio = 20;
            
            g2d.setColor(new Color(255, 0, 0));
   	     	g2d.fillRect((cylWidth*i) +(i*10) + 20, 30, cylWidth, cylHeightRatio*p.getTimeLeft());
   	     	g2d.setColor(new Color(0, 0, 0));
   	     	g2d.drawRect((cylWidth*i) +(i*10) + 20, 30, cylWidth, cylHeightRatio*p.getTimeLeft());
   	     	
   	     	g2d.drawString(String.valueOf(p.getPriority()), (cylWidth*i) +(i*10) + 22, 15);
   	     	
   	     	
   	     	g2d.setColor(new Color(255, 0, 0));
   	     	g2d.drawString(String.valueOf(p.getTimeLeft()), (cylWidth*i) +(i*10) + 22, 190);
   	     	
   	     	g2d.setColor(new Color(0, 0, 0));
	     	g2d.drawString("Œredni czas oczekiwania: "+simulation.averageWaitTime(),400,20);
	     	g2d.drawString("Iloœæ rotacji RR : "+simulation.getRotations(),400,40);
   	     	
   	     	i++;
        	
        }
    }

}
