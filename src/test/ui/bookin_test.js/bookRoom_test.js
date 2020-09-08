Feature('bookRoom');

Scenario('test something', (I) => {
    I.amOnPage('/')
    I.see('Check Availability')
    I.click('body > app-root > div > div.room-availabilty > div:nth-child(2) > mat-form-field > div > div.mat-form-field-flex > div');
    I.type('2020-12-22')
    I.click('Check');
    I.see('\"rooms_available\": 10,');
});
