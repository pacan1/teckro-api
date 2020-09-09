Feature('bookRoom');

Scenario('Check a date returns number of available days', (I) => {
    const today = Date();
    const parsed_date = formatDate(today)
    console.log("parsed date " + parsed_date);
    
    I.amOnPage('/')
    I.see('Check Availability')
    I.click('body > app-root > div > div.room-availabilty > div:nth-child(2) > mat-form-field > div > div.mat-form-field-flex > div');
    I.type(parsed_date)
    I.click('Check');
    I.see('\"rooms_available\": 10,');
});

Scenario('test something', (I) => {
    const today = Date();
    const parsed_date = formatDate(today)
    console.log("parsed date " + parsed_date);
    
    I.amOnPage('/')
    I.see('Check Availability')
    I.click('body > app-root > div > div.room-availabilty > div:nth-child(2) > mat-form-field > div > div.mat-form-field-flex > div');
    I.type(parsed_date)
    I.click('Check');
    I.see('nothing');
});


function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) 
        month = '0' + month;
    if (day.length < 2) 
        day = '0' + day;

    return [year, month, day].join('-');
}
