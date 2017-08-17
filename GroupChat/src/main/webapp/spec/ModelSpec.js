describe("Tests for message model", function () {
    var message;

    beforeEach(function () {
        message = new app.messageModel({
            message: 'prueba',
            sender: 'George',
            received: '2016-01-01'
        });
    })

    it("can have default properties such as message,sender, received", function () {
        expect(message.defaults).toBeDefined();
        expect(message.defaults.message).toBeDefined();
        expect(message.defaults.sender).toBeDefined();
        expect(message.defaults.received).toBeDefined();
    });

});


describe("Tests for youtube", function () {
    results = {
        "name": "test",
        "items": [
            {"prueba": "prueba", "id": {"videoId": "test"}}
        ]
    };

    console.log(results);
    console.log(results);
    it("must return and iframe", function () {
        expect(showResults(results, 'prueba')).toEqual('<iframe id="ytplayer" type="text/html" width="640" height="360" src="http://www.youtube.com/embed/test" frameborder="0"/>')

    });

});

describe('Message Collection', function() {
  
  evt = {"data" :  
            '{"message":"prueba","sender":"test","received":"Thu Mar 16 16:36:34 COT 2017"}'};
  console.log(evt);
  onMessageReceived(evt);
  it('should be defined', function() {
    expect(app.MessageList).toBeDefined();
  });

  it('Must be instantiated', function() {
    expect(app.MessageList).not.toBeNull();
  });
  
  it('Must add message onMessageReceived', function() {
    expect(app.MessageList.length).toEqual(2);
  });
});