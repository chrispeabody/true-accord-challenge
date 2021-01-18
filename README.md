## SETUP INSTRUCTIONS

If you're reading this it, it means you found the repository! If you don't have it yet, clone the repository to your machine or unzip the zipped file I provided.

Here's the clone command:
> git clone https://github.com/chrispeabody/true-accord-challenge.git

There are two dependencies you'll need before you can run the application:
* JDK 15 (Java Development Kit 15)
    * Any version of 15 should work, but I used 15.0.1
    * Download: https://www.oracle.com/java/technologies/javase-jdk15-downloads.html
    * Installation instructions: https://docs.oracle.com/en/java/javase/13/install/overview-jdk-installation.html#GUID-8677A77F-231A-40F7-98B9-1FD0B48C346A
* Gradle 6.7
    * Any version of 6.7 should work, but I used 6.7.1
    * Versions of 6.8 should work, but I haven't tested that
    * Download: https://gradle.org/releases/
    * Installation instructions: https://gradle.org/install/

If you know how to obtain these using package managers, that should work theoretically. Check your versions in the CLI with:
* javac -version
* gradle -version

Once you have them, you're ready to build. See below.

## BUILDING / RUNNING INSTRUCTIONS

Building the project will pull in the dependencies for the project itself. With gradle, building also runs the unit tests. To builds, use:
> gradle build

The first time the project is built, you'll see the unit tests run. If you'd like to see the unit tests again in subsequence builds, clean the build first with:
> gradle clean

If you'd like to run the tests without first building, you can use the command below. Like building, the output will not be shown after the first run unless you clean beforehand:
> gradle test

To run the project, use the below run command. If the project isn't built either due to not running the build command or having cleaned the build, this will build the project. You will not see the 
test out though:
> gradle run

Running the project will hit the endpoints described in the problem summary, run the required calculations, and print the desired output to the console.

## ASSUMPTIONS

Below are the various assumptions I made about the problem summary and system as a whole, which factored into my design decisions:

#### Assumptions about system overall
* The API will never return null for any properties of the objects they supply.
* The documentation mentions dates will be in ISO 8601, but the endpoint does not supply dates in that format. Assuming this is a mistake, but I built the application to handle them in the way they 
were actually supplied. I assume the dates are midnight in UTC for the given date.
* A debt's 'amount' value corresponds to the original, fully unpaid debt. Making payments does not reduce the 'amount' value on the debt itself.
* While payment plans are one-to-one with debts, they are not necessarily meant to pay off the entire debt. Therefore, amount_to_pay may not equal a debt's value over all and has no relation to how 
many payments have been made so far.
* The most reliable way to calculate the remaining debt to be paid is subtracting all the payment amounts for a debt from the debt's value. Since we cannot guarantee payments will come in on schedule,
 we cannot rely completely on payment plan metadata.
* Assumptions about calculating the next_payment_due_date:
    * The first payment date in the schedule is the start date of the plan.
    * WEEKLY payment plans have a payment date every 7 days after the start date.
    * BI_WEEKLY payment plans have a payment date every 14 days after the start date.
    * The time a payment occurs does not affect the days that payments are due going forward.
    * The next payment date will correspond to the oldest unpaid payment date in the schedule, even if it is past due.
    * When a payment is received, it contributes to the earliest unpaid payment date.
    * The expected installment amount on a payment date must be met in order for that day to be considered paid. The next time a payment is received, it will go toward filling out any deficits on previous payment days before being applied to the next
    * If a payment exceeds the expected installment amount for a payment date, the surplus will go toward paying the next payment date

#### Assumptions about code standard
* Unit tests don't need documentation as long as the naming conventions and comments make clear the goal of the test.
* Simple getters and setters don't need function documentation.
* The 'real' and 'json number' types mentioned in the problem summary are fine to be represented with doubles in code, since they do not exist in java.
* Dates don't need to remain Strings throughout the code.
* We can assume all of the data objects and list are non-mutable, as we will not need to perform operations that change them after they are retrieved.

#### Assumptions about printing
* The new field names mentioned in the problem summary pertain only to how they should be printed, not the literal name in the code.
* If a next_payment_due_date is null, the word 'null' should be printed.

## APPROACH / POST MORTEM

The approach I went for was a clean, extendable design with thorough testing done in Java 15. I kept things relatively simple, but the build structure, testing, and extendability pieces added some 
heft to the number of files. The fidelity of the code did add more time to the project than would have been needed if we only desired a 'quick and dirty' application, but I think the approach I took 
was worth the extra work.

I took a test-driven approach, writing stub class functions until I finished writing most/all the tests for each of the core files. This did add extra time until MVP, but drastically reduced the 
amount of time developing and bug-testing core functionality.

#### Architecture

The entry point for the program is the 'App.java' file. I kept this as light as I could, relegating core functionality to other files. Key pieces were as follows:
* **Debt, Payment, PaymentPlan, DebtInfo** - These are data objects, which (except for DebtInfo) do not hold any 'functionality'. These objects are purely a place to hold the information returned by 
the endpoints. I opted to unpack retrieved data in data objects for purpose of extendability and ease of operating on it. The 'quick and dirty' method would be to just store the JSON returned in JSON 
objects and leave it at that, but these custom objects give us more control over storing and using the data.

* **DataRetriever and APIDataRetriever** - DataRetriever is an interface meant to define a pact for obtaining debts, payment plans, and payments. While it is not utilized in this application directly, 
it allows us to add more methods of data retrieval if desired. For example, we may desire to retrieve our data from a file, an API, or a local database. APIDataRetriever is one implementation of the 
interface meant to interact specifically with the API described in the problem summary.

* **DebtAnalysisService** - This class is responsible for taking the debts, payment plans, and payments and calculating the extra information that needs to be printed. Specifically, this exposes 
functions for finding is_on_payment_plan, remaining_amount, and next_payment_due_date. It requires the full lists of debts, payments, and payment plans to operate correctly. It creates DebtInfo data
objects which have all desired information for printing, and the capability to do so.

#### Post Mortem

Overall I think the application ended in a good spot, though there are a couple places I think the code / process I used could be improved:
* When I first created the data objects, I opted for 'nullable' field types such as Boolean and Integer rather than primitives. This was on purpose for the sake of flexibility in the code. Later I
found this only lead to headache, as I needed to add exception after exception and unit test after unit test to account for possible null values. I eventually migrated to primitive types, and was 
thankful I did. I only wish I did it earlier.
* I did not complete unit tests for the APIDataRetriever. To make the class I used java's bare-bones HttpClient package. As far as I could find, most unit testing packages for mocking endpoints were 
built to work with the more popular HTTP packages such as the Apache and Google HTTP solutions. I assume a solution for mocking endpoints for basic java HttpClients exists, but for the sake of time I 
opted to skip these tests in lieu of completing core functionality.
* I opted not to add a test class for the App entry-point. While I could have probably come up with something, this would run into similar problems with end-point mocking and would have stood as more 
of an integration test than a unit test. If I had more time, I would consider adding testing of some kind here.
* The APIDataRetriever has a variable for the root URL to hit. If I had more time, I would allow a command line argument to be passed into the application to define what endpoint to hit. This required
more exception handling and documentation, so I left it alone. If I did this, I would like to create a '-h' help command for the app.
* When initially designing the architecture, I included a TextMenuService for taking inputs from and pushing outputs to the CLI. After spending some time building the scaffolding and some unit tests, 
I realized this was completely unnecessary for this app. This lead to some wasted time, but I moved past it quickly. Down the road, if the application became a full 'admin' utility as described, menus
could be useful to expose different pieces of functionality to the users. Though, a similar thing can be done with program arguments, so it's use is questionable.

I had a lot of fun creating this application, and I learned quite a bit. I'd love to talk about this more if you have any questions!

- Chris Peabody