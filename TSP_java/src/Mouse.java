import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Mouse extends MouseAdapter {

	private Main demo;
	public String clusteringMethod;
	public int counter;
	public int countSales;
	
	public Mouse(Main demo) {
		this.demo = demo;
	}

	public void mouseMoved(MouseEvent e) {
	}
	public void mouseDragged(MouseEvent e)
	{
	}
	public void mousePressed(MouseEvent e) {
		
		System.out.println(e.getX()+" "+e.getY());
		
		// K-means button
		if(e.getX()>= demo.xForButtons && e.getX()<=demo.xForButtons+90 && e.getY()>= 80 && e.getY() <= 130)
		{
			demo.cluster.main(demo.cluster);
        	clusteringMethod = "or";
        	demo.clus = true;
        	demo.bru=false;
        	demo.beterClus=false;
        	demo.readOutPut = false;
        	demo.which=0; 
        	demo.mode = 0;
        	demo.repaint();
		}
		// K amount button
		if(e.getX()>= demo.xForButtons+110 && e.getX()<=demo.xForButtons+160 && e.getY()>= 220 && e.getY() <= 270)
		{
			demo.kAmount = (demo.kAmount+1) %4;
			demo.data.k = demo.kAmount;
			demo.repaint();
		}
		// AddNode button
		if(e.getX()>= demo.xForButtons && e.getX()<=demo.xForButtons+90 && e.getY()>= 220 && e.getY() <= 270)
		{	
			if(counter%2 == 0)
				demo.addNode = true;
			else
				demo.addNode = false;
			
			counter++;
			demo.repaint();
		}
		else if(demo.addNode)
		{
			// determine the coordinates
			int x , y;
			demo.data.k = demo.kAmount;
			x = (e.getX()+5)/demo.data.zoom;
            y = -( e.getY() - (160*demo.data.zoom) + 5 ) / demo.data.zoom;
			// call data to add a node on the determined coordinates
            System.out.println(x+" eerere  "+y);
            System.out.println("totalNum = "+demo.data.TOTALNODES);
			demo.data.addNode(x, y,demo.kAmount);
			demo.repaint();
		}
		
		// AddSales button
		else if(e.getX()>= demo.xForButtons && e.getX()<=demo.xForButtons+90 && e.getY()>= 290 && e.getY() <= 340)
		{	
			if(countSales%2 == 0)
				demo.addSales = true;
			else
				demo.addSales = false;
			
			countSales++;
			demo.repaint();
		}
		else if(demo.addSales)
		{
			// determine the coordinates
			int x , y;
			x = (e.getX()+9)/demo.data.zoom;
            y = -( e.getY() - (160*demo.data.zoom) + 9 ) / demo.data.zoom;
			// call data to add a node on the determined coordinates
			demo.data.addSales(x, y);
			demo.repaint();
		}
		

	}
	public void mouseReleased(MouseEvent e) {
		
		if(e.getX()>= 0 && e.getX()<=365 && e.getY()>= 0 && e.getY()<=365)
		{
		}
	}
}