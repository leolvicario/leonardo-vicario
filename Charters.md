### Exploratory Testing Charters
##### App: Monefy
##### Platform: iOS
<br/>

1. Explore **income addition** to current month with **income amount**, **descriptive note** and **category**, to discover a change in the upper value from the center of the chart, the **balance** to be **equal** to the **entered amount** and the “Balance” button should be green.

2. Explore **income addition** to current month with **income amount** of **$0**, to discover when **pressing** the **“Choose category” button**, if $0 is a valid value.

3. Explore **expense addition** to current month with **value**, **descriptive note** and **category** by clicking the **circular “-” button**, to discover a **changes in the chart and balance**.

4. Explore the **quick expense addition** to a category by **clicking** in a **random category** and **entering a value**, to discover after submission a **change in the chart and balance**.

5. Explore **negative balance** with **expenses exceeding income** to discover **overall app behavior with negative arithmetic calculations**

6. Explore **Transfer between accounts** with **amount**, and **descriptive note**, to discover **no change** in **“income”** when **“All accounts”** info is displayed, but **only** when **selecting specific accounts**.

7.  Explore **percentages** consumed by **categories** by adding an **different amounts to expenses categories**, to discover a **change in the chart, balance and percentages in real time**.

8. Explore **changing chart views** from the left panel to discover the **navigation mechanism between days**.

9. Explore **toggling all movements** by pressing the “Balance” button to discover a **scrollable list** ordered by categories (as default) and each **row can be expanded** to see details of the movements.

10. Explore **changing movements ordering** by clicking the icon placed on the upper right side of the expanded list of movements, to discover that the **ordering now is per day** and each day can be expanded to display details of the movements.

11. Explore **adding a new account** with **name** and an **initial balance** by entering the accounts menu to discover **new entry** in accounts and an **overall increase in balance**.

12. Explore **account removal** with **registered movements** to discover the **impact in balance and movements**.

13. Explore **resetting all data** to discover **accounts, movements,  intervals, language and currency** behavior.

14. Explore **closing the app** and **dismissing it from background** to discover **data persistence**.
<br/>

### Charters Execution
#### Time spent with app: 2hs
#### Time spent writing document: 1hr
##### System under test: iPhone 12 Pro - iOS 14 - English as primary language
<br/>

It was noted during the session that the transference between accounts is only displayed in Balance when one of the involved accounts is selected and not when "All accounts" is. It's true that it doesn't impact the  overall balance but it should be listed with the amount detailed not marked as either income or expense.

It was seen that when selecting different views (days, weeks, months and years), the navigation inside the selected option is not possible.
Example: select "day" and user will only see current day with no possible navigation to previous days, same happens for week, month and year.
This feature is present in the application since when a date is selected this "before and after" navigation appears, even for movements.
The overall functionality around this feature results in a poor user experience.

The application's color coded balance mechanism although functional doesn't take into consideration color blind people which can lead into confusion if not familiar with the app. As mentioned, the phone's primary language was english, when changing the app's language to Portuguese, Italian, Spanish, German, and many other, a minus symbol was introduced when balance was below $0. Leading to treat it as a bug.
More on the subject, when leaving the input for adding expense or income in $0 and attempt to add a category, the input changes it color or a split second, again, not being inclusive for color blind users. No error message was seen, treat as a bug.

Resetting data through the "Clear data" option in settings restores the "factory" accounts, sets the balances to $0 but doesn't erase the date intervals or the changes in currency or language back to original values. This might not be considered data itself but are user's preferences which should have a reset mechanism.

The lack of time navigation between selected option and the minus symbol would be considered bugs since the option/symbol is present in the app, just not displayed until taken some actions.

The lack of error message will be considered a lack of definition in AC.

The "clear data" not working for all information or not having the option to erase users preferences should be discussed with business, if confirmed that app should be "reset", treat as bug, if not, missing requirement.
<br/>

### Prioritization
<br/>
The application under test is meant to keep track of income and expenses from user, for prioritizing the testing I will focus on the MVP which is adding values for both and monitor the arithmetic calculations for overall income, overall expenses, balance result and percentages for each category of expenses.
It is implied that data storage is equally important for the application.

On a second level I would focus on navigation through historical records, multiple accounts, and then accessibility features.
Based on user feedback it could be determined if efforts should be better placed in time navigation or multiple accounts first.

*Note: Only non-premium features were taken into consideration for this challenge.*
<br/>

### Time Spent - Breakdown
<br/>
As an average, the time thought for each charter was of 5 minutes, but, time combinations, languages and currencies took longer than expected.
Balance operations took some extra time but was expected since some arithmetic calculations were made to check app results (attempted values with point decimal to check sum of values and percentages, which round to upper values).
<br/>

### Risk Mitigation
<br/>
Since dealing with multiple accounts, multiple currencies, and date filters, it should be paramount to focus on the correct treatment of them. Whether we have two to n accounts, the overall balance should be on point taking them into consideration, applying correct currency values if they differ between one and other, also for the time frames that the user wants to display.<br/>
It ends up being a matter of values taking into consideration for arithmetic operations, so we should follow their principles to ensure correct behavior and proper display of data taking into consideration the multiple factors that might apply.<br/>
It is strongly recommended to apply a robust layer of unit tests around mathematic calculations (including currencies conversion) and dates selection. If using any third parties for currency values, build mocks around the actual call, to reduce the integration testing until a point that only environment variables and actual connection is tested. By doing this, we trim down the UI layer testing extensively, saving time and resources.
