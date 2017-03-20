define(['handlebars'], function(Handlebars) {

this["JST"] = this["JST"] || {};

this["JST"]["app/scripts/templates/chat.hbs"] = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Handlebars.helpers); data = data || {};
  var buffer = "", stack1, functionType="function", escapeExpression=this.escapeExpression, self=this;

function program1(depth0,data) {
  
  var buffer = "", stack1;
  buffer += "\n                <li class=\"left clearfix\">\n                <div class=\"chat-body1 clearfix\">\n                    <p>";
  stack1 = ((stack1 = (depth0 && depth0.msg)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1);
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "</p>\n                    <div class=\"chat_time pull-right\">"
    + escapeExpression(((stack1 = (depth0 && depth0.date)),typeof stack1 === functionType ? stack1.apply(depth0) : stack1))
    + "</div>\n                  </div>\n                </li>\n              ";
  return buffer;
  }

  buffer += "<div class=\"main_section\">\n  <div class=\"container\">\n    <div class=\"chat_container\">\n\n      <div class=\"col-sm-12 message_section\">\n        <div class=\"row\">\n          <div class=\"chat_area\">\n            <ul class=\"list-unstyled\">\n              ";
  stack1 = helpers.each.call(depth0, (depth0 && depth0.collection), {hash:{},inverse:self.noop,fn:self.program(1, program1, data),data:data});
  if(stack1 || stack1 === 0) { buffer += stack1; }
  buffer += "\n            </ul>\n          </div>\n\n            <form class=\"message_write\" id=\"chat-form\">\n              <input id=\"message\" class=\"form-control\" placeholder=\"Type a message\">\n              <div class=\"clearfix\"></div>\n              <button type=\"submit\" class=\"pull-right btn btn-success\">Send</button>\n            </form>\n          </div>\n\n        </div>\n      </div>\n    </div>\n  </div>\n</div>\n";
  return buffer;
  });

this["JST"]["app/scripts/templates/login.hbs"] = Handlebars.template(function (Handlebars,depth0,helpers,partials,data) {
  this.compilerInfo = [4,'>= 1.0.0'];
helpers = this.merge(helpers, Handlebars.helpers); data = data || {};
  


  return "<div class=\"jumbotron\">\n  <h1>Hello!</h1>\n  <form id=\"login-form\">\n    <div class=\"form-group\">\n      <label for=\"nickname\">Nickname</label>\n      <input type=\"text\" class=\"form-control\" id=\"nickname\" placeholder=\"Nickname\" required>\n    </div>\n    <button type=\"submit\" class=\"btn btn-default\">Sign In</button>\n  </form>\n</div>\n";
  });

return this["JST"];

});