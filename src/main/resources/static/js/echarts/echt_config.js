var sss = [{
    "name": "测试",
    "type": "line",
    "stack": "",
    "smooth": true,
    "areaStyle": {},
    "data": [10, 5, 100],
    "id": "1543578327000line测试"
}, {
    "name": "测试",
    "type": "line",
    "stack": "",
    "smooth": true,
    "areaStyle": {},
    "data": [],
    "id": "1543579512000line测试"
}, {
    "name": "测试",
    "type": "line",
    "stack": "",
    "smooth": true,
    "areaStyle": {},
    "data": [],
    "id": "1543809726000line测试"
},
];

function f() {
    var length = _arr.length;
    for (var i = 0; i < length; i++) {
        if (_arr[i] == _obj) {
            if (i == 0) {
                _arr.shift(); //删除并返回数组的第一个元素
                return _arr;
            }
            else if (i == length - 1) {
                _arr.pop();  //删除并返回数组的最后一个元素
                return _arr;
            }
            else {
                _arr.splice(i, 1); //删除下标为i的元素
                return _arr;
            }
        }
    }
}

