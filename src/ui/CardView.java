/* ATTENTION NORMALEMENT CE FICHIER PEUT ETRE SUPPRIMME SI CARD.java EST OK 

package ui;
import javax.swing.*;
import game.CardData;
import java.awt.*;


public class CardView extends JPanel {
    private CardData data;
    private boolean isFaceUp = false;

    public CardView(CardData data) {
        this.data = data;
        this.setPreferredSize(new Dimension(100, 140));
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1, true));

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // if the card is hidden, it must be returned
                if (!isFaceUp) {
                    setFaceUp(true);
                    System.out.println("reveal card : " + data.getName());
                }
            }
        });
    }

    public void setFaceUp(boolean faceUp) {
        this.isFaceUp = faceUp;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isFaceUp) {
            // color define in CardData.java
            g2.setColor(data.getColor());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

            // number in top left and down right 
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 16));//UPDATE POLICE 
            g2.drawString(String.valueOf(data.getValue()), 10, 25); //top left 
            g2.drawString(String.valueOf(data.getValue()), getWidth() - 25, getHeight() - 15); //down right 

            // middle prof name 
            g2.setFont(new Font("Arial", Font.BOLD, 12));//UPDATE POLICE 
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(data.getName())) / 2;
            g2.drawString(data.getName(), x, getHeight() / 2);

        } else {
            // card face down 
            g2.setColor(new Color(0, 85, 164));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.ITALIC, 18));//UPDATE POLICE 
            g2.drawString("SKYJO", 20, getHeight() / 2);
        }
    }
}

*/