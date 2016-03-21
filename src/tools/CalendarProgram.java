package tools;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class CalendarProgram extends JPanel implements ActionListener {
	public Calendar chosenDate; // The date selected in the panel.
	private Color chosenDateButtonColor; // The color for the selected date.
	private Color chosenMonthButtonColor; // The color for dates in the current
											// month.
	private Color chosenOtherButtonColor; // The color for dates that are
											// visible, but not in the current
											// month.
	private int firstDayOfWeek; // The first day-of-the-week.
	private int yearSelectionRange = 20; // The range used for selecting years.
	private Font dateFont = new Font("SansSerif", Font.PLAIN, 10); // The font
																	// used to
																	// display
																	// the date.
	private JComboBox monthSelector; // A combo for selecting the month.
	private JComboBox yearSelector; // A combo for selecting the year.
	private JButton todayButton; // A button for selecting today's date.
	private JButton[] buttons; // An array of buttons used to display the
								// days-of-the-month.
	private boolean refreshing = false; // A flag that indicates whether or not
										// we are currently refreshing the
										// buttons.
	private int[] WEEK_DAYS; // The ordered set of all seven days of a week,
								// beginning with the 'firstDayOfWeek'.
	private Formatter fmt;

	public CalendarProgram() {
		this(Calendar.getInstance(), false);
	}

	public CalendarProgram(final Calendar calendar, final boolean controlPanel) {
		super(new BorderLayout());
		chosenDateButtonColor = UIManager.getColor("textHighlight");
		chosenMonthButtonColor = UIManager.getColor("control");
		chosenOtherButtonColor = UIManager.getColor("controlShadow");
		chosenDate = calendar; // the default date is today...
		firstDayOfWeek = calendar.getFirstDayOfWeek();
		WEEK_DAYS = new int[7];
		for (int i = 0; i < 7; i++) {
			WEEK_DAYS[i] = ((firstDayOfWeek + i - 1) % 7) + 1;
		}
		add(constructSelectionPanel(), BorderLayout.NORTH);
		add(getCalendarPanel(), BorderLayout.CENTER);
		if (controlPanel) {
			add(constructControlPanel(), BorderLayout.SOUTH);
		}
		setDate(calendar.getTime());
	}

	public void setDate(final Date theDate) {
		/** * Sets the date chosen in the panel.theDate the new date. */
		chosenDate.setTime(theDate);
		monthSelector.setSelectedIndex(chosenDate.get(Calendar.MONTH));
		refreshYearSelector();
		refreshButtons();
	}

	public String getDate() {
		fmt = new Formatter();
		fmt.format("%tF", chosenDate);
		return fmt.toString();
	}

	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equals("monthSelectionChanged")) {
			final JComboBox c = (JComboBox) e.getSource();
			int dayOfMonth = chosenDate.get(Calendar.DAY_OF_MONTH);
			chosenDate.set(Calendar.DAY_OF_MONTH, 1);
			chosenDate.set(Calendar.MONTH, c.getSelectedIndex());
			int maxDayOfMonth = chosenDate
					.getActualMaximum(Calendar.DAY_OF_MONTH);
			chosenDate.set(Calendar.DAY_OF_MONTH,
					Math.min(dayOfMonth, maxDayOfMonth));
			refreshButtons();
		} else if (e.getActionCommand().equals("yearSelectionChanged")) {
			if (!refreshing) {
				final JComboBox c = (JComboBox) e.getSource();
				final Integer y = (Integer) c.getSelectedItem();
				int dayOfMonth = chosenDate.get(Calendar.DAY_OF_MONTH);
				chosenDate.set(Calendar.DAY_OF_MONTH, 1);
				chosenDate.set(Calendar.YEAR, y.intValue());
				int maxDayOfMonth = chosenDate
						.getActualMaximum(Calendar.DAY_OF_MONTH);
				chosenDate.set(Calendar.DAY_OF_MONTH,
						Math.min(dayOfMonth, maxDayOfMonth));
				refreshYearSelector();
				refreshButtons();
			}
		} else if (e.getActionCommand().equals("todayButtonClicked")) {
			setDate(new Date());
		} else if (e.getActionCommand().equals("dateButtonClicked")) {
			final JButton b = (JButton) e.getSource();
			final int i = Integer.parseInt(b.getName());
			final Calendar cal = getFirstVisibleDate();
			cal.add(Calendar.DATE, i);
			setDate(cal.getTime());
		}
	}

	private JPanel getCalendarPanel() {
		final JPanel p = new JPanel(new GridLayout(7, 7));
		final DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
		final String[] weekDays = dateFormatSymbols.getShortWeekdays();
		for (int i = 0; i < WEEK_DAYS.length; i++) {
			p.add(new JLabel(weekDays[WEEK_DAYS[i]], SwingConstants.CENTER));
		}
		buttons = new JButton[42];
		for (int i = 0; i < 42; i++) {
			final JButton b = new JButton("");
			b.setMargin(new Insets(1, 1, 1, 1));
			b.setName(Integer.toString(i));
			b.setFont(dateFont);
			b.setFocusPainted(false);
			b.setActionCommand("dateButtonClicked");
			b.addActionListener(this);
			buttons[i] = b;
			p.add(b);
		}
		return p;
	}

	private Color getButtonColor(final Calendar theDate) {
		if (equalDates(theDate, chosenDate)) {
			return chosenDateButtonColor;
		} else if (theDate.get(Calendar.MONTH) == chosenDate
				.get(Calendar.MONTH)) {
			return chosenMonthButtonColor;
		} else {
			return chosenOtherButtonColor;
		}
	}

	private boolean equalDates(final Calendar c1, final Calendar c2) {
		if ((c1.get(Calendar.DATE) == c2.get(Calendar.DATE))
				&& (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
				&& (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR))) {
			return true;
		} else {
			return false;
		}
	}

	private Calendar getFirstVisibleDate() {
		final Calendar c = Calendar.getInstance();
		c.set(chosenDate.get(Calendar.YEAR), chosenDate.get(Calendar.MONTH), 1);
		c.add(Calendar.DATE, -1);
		while (c.get(Calendar.DAY_OF_WEEK) != getFirstDayOfWeek()) {
			c.add(Calendar.DATE, -1);
		}
		return c;
	}

	private int getFirstDayOfWeek() {
		return firstDayOfWeek;
	}

	private void refreshButtons() {
		final Calendar c = getFirstVisibleDate();
		for (int i = 0; i < 42; i++) {
			final JButton b = buttons[i];
			b.setText(Integer.toString(c.get(Calendar.DATE)));
			b.setBackground(getButtonColor(c));
			c.add(Calendar.DATE, 1);
		}
	}

	private void refreshYearSelector() {
		if (!refreshing) {
			refreshing = true;
			yearSelector.removeAllItems();
			final Integer[] years = getYears(chosenDate.get(Calendar.YEAR));
			for (int i = 0; i < years.length; i++) {
				yearSelector.addItem(years[i]);
			}
			yearSelector.setSelectedItem(new Integer(chosenDate
					.get(Calendar.YEAR)));
			refreshing = false;
		}
	}

	private Integer[] getYears(final int chosenYear) {
		final int size = yearSelectionRange * 2 + 1;
		final int start = chosenYear - yearSelectionRange;
		final Integer[] years = new Integer[size];
		for (int i = 0; i < size; i++) {
			years[i] = new Integer(i + start);
		}
		return years;
	}

	private JPanel constructSelectionPanel() {
		final JPanel p = new JPanel();
		final int minMonth = chosenDate.getMinimum(Calendar.MONTH);
		final int maxMonth = chosenDate.getMaximum(Calendar.MONTH);
		final String[] months = new String[maxMonth - minMonth + 1];
		for (int i = 0; i < months.length; i++) {
			months[i] = "" + (i + 1);
		}
		monthSelector = new JComboBox();
		monthSelector.setModel(new DefaultComboBoxModel(new String[] {
				"January", "February", "March", "April", "May", "June", "July",
				"August", "September", "October", "November", "December" }));
		monthSelector.addActionListener(this);
		monthSelector.setActionCommand("monthSelectionChanged");
		p.add(monthSelector);
		yearSelector = new JComboBox(getYears(0));
		yearSelector.addActionListener(this);
		yearSelector.setActionCommand("yearSelectionChanged");
		p.add(yearSelector);
		return p;
	}

	private JPanel constructControlPanel() {
		final JPanel p = new JPanel();
		todayButton = new JButton("Today");
		todayButton.addActionListener(this);
		todayButton.setActionCommand("todayButtonClicked");
		p.add(todayButton);
		return p;
	}

	public Color getChosenDateButtonColor() {
		return this.chosenDateButtonColor;
	}

	public void setChosenDateButtonColor(final Color chosenDateButtonColor) {
		if (chosenDateButtonColor == null) {
			throw new NullPointerException("UIColor must not be null.");
		}
		final Color oldValue = this.chosenDateButtonColor;
		this.chosenDateButtonColor = chosenDateButtonColor;
		refreshButtons();
		firePropertyChange("chosenDateButtonColor", oldValue,
				chosenDateButtonColor);
	}

	public Color getChosenMonthButtonColor() {
		return this.chosenMonthButtonColor;
	}

	public void setChosenMonthButtonColor(final Color chosenMonthButtonColor) {
		if (chosenMonthButtonColor == null) {
			throw new NullPointerException("UIColor must not be null.");
		}
		final Color oldValue = this.chosenMonthButtonColor;
		this.chosenMonthButtonColor = chosenMonthButtonColor;
		refreshButtons();
		firePropertyChange("chosenMonthButtonColor", oldValue,
				chosenMonthButtonColor);
	}

	public Color getChosenOtherButtonColor() {
		return this.chosenOtherButtonColor;
	}

	public void setChosenOtherButtonColor(final Color chosenOtherButtonColor) {
		if (chosenOtherButtonColor == null) {
			throw new NullPointerException("UIColor must not be null.");
		}
		final Color oldValue = this.chosenOtherButtonColor;
		this.chosenOtherButtonColor = chosenOtherButtonColor;
		refreshButtons();
		firePropertyChange("chosenOtherButtonColor", oldValue,
				chosenOtherButtonColor);
	}

	public int getYearSelectionRange() {
		return this.yearSelectionRange;
	}

	public void setYearSelectionRange(final int yearSelectionRange) {
		final int oldYearSelectionRange = this.yearSelectionRange;
		this.yearSelectionRange = yearSelectionRange;
		refreshYearSelector();
		firePropertyChange("yearSelectionRange", oldYearSelectionRange,
				yearSelectionRange);
	}
}