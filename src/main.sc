require: slotfilling/slotFilling.sc
  module = sys.zb-common
  
require: text/text.sc
    module = zenbot-common
    
require: where/where.sc
    module = zenbot-common

require: common.js
    module = zenbot-common
    
require: functions.js

theme: /
    
    state: Start
        q!: $regex</start>
        a: Я хочу сыграть с тобой в игру: "Какой город является столицей страны". Если готов - ответь /go, когда надоест - ответь /stop
        
    state: Go
        q!: $regex</go>
        script:
            $session = {}
            $session.score = 0
        go!: /PlayTheGame
        
    state: PlayTheGame || modal = true
        script:
            $session = {}
            $session.score = 0
            $session.fail = 0
        go!: /PlayTheGame/Ask

        state: Ask
            script:
                $session.country = $Geography[chooseRandCountryKey(Object.keys($Geography))];
                $reactions.answer("Ваш счет {{$session.score}}");
                $reactions.answer("Какой город является столицей {{$session.country.value.country}}?");
            go!: /PlayTheGame/Check


        state: Check
            state: CheckCity
                q: * $City *
                script:
                    if ($parseTree._City.name == $session.country.value.name) {
                        $reactions.answer("Верно! Плюс балл!");
                        $session.score = $session.score + 1
                    } else {
                        $reactions.answer("Ошибка! Столица {{$session.country.value.country}} - {{$session.country.value.name}}! Минус балл!");
                        $session.fail = $session.fail + 1
                    }
                go!: /PlayTheGame/Ask
                
            state: LocalCatchAll
                event: noMatch
                a: Это не город.
                
    state: Default
        event!: noMatch
        a: Это не похоже на ответ. Попробуйте еще раз.
    
    state: stop
        q!: $regex</stop>
        script:
        if: ({{$session.score}} > {{$session.fail}})
            a:  Вы назвали правильно {{$session.score}} столиц, неправильно {{$session.fail}}. Географ из вас конечно так себе
        else:
          a: Вы назвали правильно {{$session.score}} столиц, неправильно {{$session.fail}}. Ну что сказать, неплохо, неплохо...
        go!: /Start
    