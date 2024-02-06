package unibo.exiled.view.menu;

import unibo.exiled.model.menu.Command;
import unibo.exiled.view.GameView;
import unibo.exiled.view.NewGameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


/**
 * The action listener class for the menu. To retrieve input.
 */
public final class MenuItemActionListener implements ActionListener {
    private final NewGameView newGameView;
    private GameView game;

    /**
     * The constructor of the in-game menu action listener.
     *
     * @param game        The main game view.
     * @param newGameView The new-game view.
     */
    public MenuItemActionListener(final GameView game, final NewGameView newGameView) {
        super();
        this.game = game;
        this.newGameView = newGameView;
    }

    /**
     * Processes the input event.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        if ("new_game".equals(e.getActionCommand())) {
            this.game = new GameView();
            this.game.display();
            this.newGameView.hide();
        } else if (e.getActionCommand().equals(Command.CLOSE_MENU.getCommandString())) {
            this.game.hideMenu();
        } else if ("quit".equals(e.getActionCommand())) {
            final int dialogResult = JOptionPane.showConfirmDialog(null,
                    "Would you like to quit the game?", "Warning",
                    JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                if (this.game != null) {
                    this.game.close();
                } else {
                    this.newGameView.close();
                }
            }
        } else {
            throw new IllegalArgumentException("Command is not valid");
        }
    }
}
