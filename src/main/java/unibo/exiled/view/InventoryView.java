package unibo.exiled.view;

import unibo.exiled.controller.GameController;
import unibo.exiled.view.items.GameButton;
import unibo.exiled.view.items.GameLabel;
import unibo.exiled.view.items.TitleGameLabel;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

/**
 * The view panel of the player's inventory.
 */
public final class InventoryView extends JPanel {
    private static final long serialVersionUID = 2L;

    private static final Color HEALING_ITEM_COLOR = new Color(141, 254, 141);
    private static final Color POWER_UP_ITEM_COLOR = new Color(254, 141, 141);
    private static final Border LIST_ITEM_BORDER = new LineBorder(Color.BLACK, 1);
    private static final int LIST_ITEM_HEIGHT = 30;
    private static final int LEFT_RIGHT_MARGIN = 100;
    private static final int TOP_BOTTOM_MARGIN = 15;
    private final GameController gameController;
    private final DefaultListModel<String> listModel;
    private final JList<String> itemNamesList;
    private final JLabel emptyInventoryLabel;
    private final JScrollPane scrollPane;

    /**
     * The constructor of the inventory view.
     *
     * @param inventoryController The controller of the inventory.
     * @param game                The view of the game (Main view)
     */
    public InventoryView(final GameController gameController, final GameView game) {
        this.gameController = gameController;
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        itemNamesList = new JList<>(listModel);
        itemNamesList.addListSelectionListener(new ItemListSelectionListener());
        itemNamesList.setCellRenderer(new ItemListRenderer());

        final Dimension listItemSize = new Dimension(100, LIST_ITEM_HEIGHT);
        final int listItemWidth = getScreenWidth();

        itemNamesList.setFixedCellHeight(LIST_ITEM_HEIGHT);
        itemNamesList.setFixedCellWidth(listItemWidth - LEFT_RIGHT_MARGIN);
        scrollPane = new JScrollPane(itemNamesList);

        scrollPane.setSize(listItemSize);

        emptyInventoryLabel = new GameLabel("The inventory is empty");
        emptyInventoryLabel.setHorizontalAlignment(JLabel.CENTER);

        // Center
        final JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.add(scrollPane, BorderLayout.CENTER);
        centralPanel.add(emptyInventoryLabel, BorderLayout.SOUTH);
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.PAGE_AXIS));
        centralPanel.setBorder(BorderFactory.createEmptyBorder(TOP_BOTTOM_MARGIN, 0,
                TOP_BOTTOM_MARGIN, 0));
        add(centralPanel, BorderLayout.CENTER);

        // North
        final JLabel titleLabel = new TitleGameLabel("Inventory");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        final GameButton exitButton = new GameButton("Exit");
        //exitButton.addActionListener(e -> game.hideInventory());

        final JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(exitButton, BorderLayout.WEST);
        northPanel.add(titleLabel, BorderLayout.CENTER);
        northPanel.setBorder(BorderFactory.createEmptyBorder(TOP_BOTTOM_MARGIN, 0, TOP_BOTTOM_MARGIN, 0));
        add(northPanel, BorderLayout.NORTH);

        updateInventoryList();
    }

    private int getScreenWidth() {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getScreenSize().width;
    }

    /**
     * Updates the viewed list inside the inventory and repaints it.
     */
    public void updateInventoryList() {
        listModel.clear();

        final Map<String, Integer> itemsList = gameController.getItems();

        if (itemsList.isEmpty()) {
            emptyInventoryLabel.setVisible(true);
            scrollPane.setVisible(false);
        } else {
            for (final Map.Entry<String, Integer> entry : itemsList.entrySet()) {
                final String itemName = entry.getKey();
                listModel.addElement(itemName);
            }
            emptyInventoryLabel.setVisible(false);
        }

        revalidate();
        repaint();
    }

    private final class ItemListRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = 3L;

        @Override
        public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index,
                final boolean isSelected, final boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof String item) {
                final Map<String, Integer> itemsList = gameController.getItems();

                final int quantity = itemsList.get(item);
                final String description = gameController.getItemDescription(item);

                switch (gameController.getItemType(item)) {
                    case HEALTH -> {
                        setBackground(HEALING_ITEM_COLOR);
                        setText(" " + item + " - Quantity: " + quantity + " - Description: "
                                + description + " - Heal: " + gameController.getItemValor(item));
                    }

                    case POWERUP -> {
                        setBackground(POWER_UP_ITEM_COLOR);
                    setText(" "
                            + item + " - Quantity: " + quantity
                            + " - Description: "
                            + description + " - PowerUp: " + gameController.getItemValor(item) + " - Attribute: "
                            + gameController.getItemBoostedAttributeName(item) + " - Duration: "
                            + gameController.getItemDuration(item));
                    }

                    case RESOURCE -> {
                        setText(" " + item + " - Quantity: " + quantity + " - Description: "
                        + description);
                    }

                    default -> {
                    }
                }

                /*if (item instanceof HealingItem healItem) {
                    setBackground(HEALING_ITEM_COLOR);
                    setText(" " + item.getName() + " - Quantity: " + quantity + " - Description: "
                            + item.getDescription() + " - Heal: " + healItem.getAmount());
                } else if (item instanceof PowerUpItem powerUpItem) {
                    setBackground(POWER_UP_ITEM_COLOR);
                    setText(" "
                            + item.getName() + " - Quantity: " + quantity
                            + " - Description: "
                            + item.getDescription() + " - PowerUp: " + powerUpItem.getAmount() + " - Attribute: "
                            + powerUpItem.getBoostedAttribute().getName() + " - Duration: "
                            + powerUpItem.getDuration());
                } else {
                    setText(" " + item.getName() + " - Quantity: " + quantity + " - Description: "
                            + item.getDescription());
                }*/
                setBorder(LIST_ITEM_BORDER);
            }
            setHorizontalAlignment(SwingConstants.CENTER);

            return this;
        }
    }

    private final class ItemListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(final ListSelectionEvent e) {
         /*   if (!e.getValueIsAdjusting()) {
                final String selectedItemName = itemNamesList.getSelectedValue();
                if (selectedItem instanceof UsableItem usableItem) {
                    final int confirmation = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to use "
                                    + usableItem.getName() + "?",
                            "Confirm Use", JOptionPane.YES_NO_OPTION);

                    if (confirmation == JOptionPane.YES_OPTION) {
                        final boolean useResult = inventoryController.useItem(usableItem);
                        if (useResult) {
                            JOptionPane.showMessageDialog(null, "Used " + usableItem.getName());
                        } else {
                            JOptionPane.showMessageDialog(null, "Item not found in the inventory");
                        }
                        updateInventoryList();
                    }
                }
            }*/
        }
    }
}