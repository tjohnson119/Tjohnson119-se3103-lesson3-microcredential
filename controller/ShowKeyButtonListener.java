package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

public class ShowKeyButtonListener implements ItemListener{

	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox option = (JCheckBox) e.getSource();
		boolean checked= option.isSelected();
		App.gameModel.setShowKey(checked);
		App.win.updateWindow();
	}

	
	
}
