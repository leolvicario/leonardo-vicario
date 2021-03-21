##Module breakdown

As mentioned before in the project's readme, the module relies on Appium for conducting the tests, although several other libraries
were used for the tests.<br/>
If followed the prerequisites all tests can be run. Be aware of the automatic start/stop of the emulator, since it was done for a preference
of mine, but it can be omitted by commenting own the usage of those functions, just leave an emulator running before launching the tests.<br/>
Below there will be a list of the developed tests. I selected them based on the charters that I've created and after some casual usage. Basically what I did is go to the supermarket
and add an expense for each item that was picked. By doing this I was able to stick to a budget and avoid picking unnecessary things.
I'm aware that it is not the correct usage or what the app was intended for, but it showed me what needs to work and what are "bonus" or "nice to have" features.<br/>
During the development of some tests it was observed that the categories displayed in the main screen move around and that they don't have any attribute to identify them easily, 
which led to the implementation of some workarounds and an attempt of using image recognition through OpenCV which couldn't be tested due to some machine limitations. 
An alternative for this was going to be "Tesseract" (works in the machine, consists in taking snapshot of device screen, and look if it contains an image, 
also it would enable strings recognition such as the percentages) but there was more coverage to be done.<br/>
Another useful feature to have implemented was "soft assertions" to check values which may not transcendent for the app functionality, but testing coverage 
didn't include that kind of information.<br/>

###Tests to be developed by order of importance

1. Adding income
2. Adding expense (quick)
3. Adding expense
4. Data Persistence
5. Clear Data
6. Transfer (negative and positive balance)
7. Categories percentages
8. Navigation - MISSING
9. Adding account with balance
10. Deleting account with movements - MISSING

###Grouping tests
There are several ways of grouping these tests. What would be a correct approach is to create a smoke suite for the most common features
and data persistence to ensure MVP is up for delivery. Then add a second suite built around "bonus" or "nice to have" features
linked to MVP features (both free and premium). Finally, create a third suite for the remaining tests.<br/>
By following the approach suggested above, we can quickly determine if the app can be delivered to end users by the results of the first suite only, which should 
take a limited amount of time. The second suite will look for bugs to be fixed by "hot fixes", and the third one, for next release candidate.
If taken the above as tests belonging to an entire suite, the 1st, 2nd, 3rd, 4th and 5th will be placed under this first group,
6th, 7th and 8th will be placed under the second group, and, the remaining ones to the third group.

####Alternative
Other way to group the tests could be by feature. This approach will give more visibility to each particular feature instead of the full application.
To mitigate this, tests could be organized to execute in a given order, so when a feature's report is shown, the core functionality would be displayed first.

###Tests summary
All above cases can be automated, but some represent an issue if not dealing with premium version.<br/>
Most of the tests above have steps such as registering movements to analise in next steps, that makes them prone to fails if
some feature does not behave as expected, which can compromise the feature under test by throwing false failures or successes or even skip the test.
Through the premium version, since data is stored in the cloud, there could be some public API to generate movements or retrieve existing ones to be compared in the UI,
trimming this layer of testing and making the same more robust.<br/>
Moreover, as mentioned before, the category images should include an attribute such as "content-desc" to be able to identify them
properly if they are supposed to move around the screen. Using image recognition although helpful can be expensive or prone to fail.

