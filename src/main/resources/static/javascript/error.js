const messages = [
    ['Whoops.', 'Oops.', 'Excuse me.', 'Oh Dear.', 'Well poo.', 'Hm...', 'This is awkward.', 'Well gosh!'],
    ['It appears', 'Looks like', 'Unfortunately,', 'It just so happens', 'Sadly,', 'Seemingly from nowhere'],
    ['there was an error.', 'we goofed up.', 'a bad thing happend.', 'the server crashed.', 'a bug appeared.', 'someone did a naughty.', 'pixies got into the server!', 'the server threw a tantrum.', 'the website had a bad day.', 'our code pooped out.'],
    ['Sorry.', 'Apologies.', 'Our bad.', 'Sad day.', 'We are quite contrite.', 'Beg pardon.']
];
const messageElements = [
    document.querySelector('#js-whoops'),
    document.querySelector('#js-appears'),
    document.querySelector('#js-error'),
    document.querySelector('#js-apology')
];

const widthElement = document.querySelector('#js-hidden');

let lastMessageType = -1;

let messageTimer = 4000;


document.addEventListener('DOMContentLoaded', (event) => {
    setupMessages();
    setInterval(() => {
        swapMessage();
    }, messageTimer);
});

function setupMessages() {
    messageElements.forEach((element, index) => {
        let newMessage = getNewMessage(index);
        element.innerText = newMessage;
    });
}

function calculateWidth(element, message) {

    widthElement.innerText = message;
    let newWidth = widthElement.getBoundingClientRect().width;
    element.style.width = `${newWidth}px`;
}

function swapMessage() {
    let toSwapIndex = getNewSwapIndex();
    let newMessage  = getNewMessage(toSwapIndex);
    messageElements[toSwapIndex].style.lineHeight = '0';
    setTimeout(() => {
        checkWidthSet(toSwapIndex, messageElements[toSwapIndex].innerText);
        messageElements[toSwapIndex].innerText = newMessage;
        calculateWidth(messageElements[toSwapIndex], newMessage);
    }, 200);
    setTimeout(() => {
        messageElements[toSwapIndex].style.lineHeight = '1.2';
    }, 400);
}
function checkWidthSet(index, message) {
    if (false === messageElements[index].style.width) {
        messageElements[index].style.width = `${messageElements[index].clientWidth}px`;
    }
}
function getNewSwapIndex() {
    let newMessageIndex = Math.floor(Math.random() * messages.length);
    while (lastMessageType === newMessageIndex) {
        newMessageIndex = Math.floor(Math.random() * messages.length);
    }
    return newMessageIndex;
}

function getNewMessage(toSwapIndex) {
    const messagesArray   = messages[toSwapIndex];
    const previousMessage = messageElements[toSwapIndex].innerText;
    // Get a new random index and the message at that index
    let newMessageIndex = Math.floor(Math.random() * messagesArray.length);
    let newMessage      = messagesArray[newMessageIndex];
    // let's make sure they aren't the same as the message already there
    while (newMessage === previousMessage) {
        newMessageIndex = Math.floor(Math.random() * messagesArray.length);
        newMessage      = messagesArray[newMessageIndex];
    }
    return newMessage;
}