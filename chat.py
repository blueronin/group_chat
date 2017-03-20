#!/bin/env python
import os
from app import create_app, socketio

app = create_app(debug=True)

if __name__ == '__main__':
    port = int(os.environ.get("PORT", 80))
    app.run(host='0.0.0.0', port=port)
    socketio.run(app)
