import { ArgularTeradataPage } from './app.po';

describe('argular-teradata App', () => {
  let page: ArgularTeradataPage;

  beforeEach(() => {
    page = new ArgularTeradataPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
