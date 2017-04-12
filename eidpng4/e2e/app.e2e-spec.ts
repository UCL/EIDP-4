import { Eidpng4Page } from './app.po';

describe('eidpng4 App', () => {
  let page: Eidpng4Page;

  beforeEach(() => {
    page = new Eidpng4Page();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
