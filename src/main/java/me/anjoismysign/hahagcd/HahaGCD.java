package me.anjoismysign.hahagcd;

import me.anjoismysign.anjo.entities.Result;
import me.anjoismysign.anjo.swing.AnjoPane;
import me.anjoismysign.anjo.swing.components.AnjoTextField;
import me.anjoismysign.anjo.swing.listeners.TextInputType;
import me.anjoismysign.hahaswing.Bubble;
import me.anjoismysign.hahaswing.BubbleFactory;

import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Color;
import java.util.Objects;

public class HahaGCD {
    public static void main(String[] args) {
        open();
    }

    private static void open(){
        Bubble<AnjoPane> bubble = BubbleFactory.getInstance()
                .smartController(anjoPane -> {
                    System.exit(0);
                        },"Gratest Common Divisor",
                        new ImageIcon(Objects.requireNonNull(HahaGCD.class.getResource("/anjoismysignature.png")))
                                .getImage().getScaledInstance(256, 256, Image.SCALE_SMOOTH),
                        null,
                        AnjoTextField.build("Greatest Number")
                                .addColorToText(TextInputType.INTEGER, Color.RED, false)
                                .addColorToText(TextInputType.INTEGER, Color.BLACK, true),
                        AnjoTextField.build("Lowest Number")
                                .addColorToText(TextInputType.INTEGER, Color.RED, false)
                                .addColorToText(TextInputType.INTEGER, Color.BLACK, true));
        bubble.onBlow(anjoPane -> {
            if (anjoPane.didCancel())
                return;
            Result<Integer> greatestResult = anjoPane.getInteger(0);
            Result<Integer> lowestResult = anjoPane.getInteger(1);
            if (!greatestResult.isValid() || !lowestResult.isValid()) {
                JOptionPane.showMessageDialog(null, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                open();
                return;
            }
            int greatest = greatestResult.value();
            int lowest = lowestResult.value();
            if (greatest < lowest) {
                JOptionPane.showMessageDialog(null, "Greatest number must be greater than lowest number", "Error", JOptionPane.ERROR_MESSAGE);
                open();
                return;
            }
            int gcd = euclideanAlgorithm(greatest, lowest);
            JOptionPane.showMessageDialog(null,
                    "gcd(" + greatest + "," + lowest + ") = "+gcd, "Result",
                    JOptionPane.INFORMATION_MESSAGE);
            open();
        });
    }

    private static int euclideanAlgorithm(int greatest, int lowest) {
        if (lowest == 0)
            return greatest;
        return euclideanAlgorithm(lowest, greatest % lowest);
    }
}
