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
    
    state: start
        q!: $regex</start>
        a: Я хочу сыграть с тобой в игру: "Какой город является столицей страны". Если готов - ответь /go, когда надоест - ответь /stop
        
    state: Go
        q!: $regex</go>
        # go!: /PlayTheGame

    state: Default
        event: noMatch
        a: Это не похоже на ответ. Попробуйте еще раз.
        
    # state: PlayTheGame
    #     script:
    #         $session = {}
    #         $session.score = 0
    #     go!: /PlayTheGame/Check

    #     state: ask
    #         script:
    #             $session.country = $Geography[chooseRandCountryKey(Object.keys($Geography))];
    #             $reactions.answer("Ваш счет {{$session.score}}");
    #             $reactions.answer("Какой город является столицей {{$session.country.value.country}}?");
    #         go!: /PlayTheGame/ask


    #     state: Check
    #         state: CheckCity
    #             q: * $City *
    #             a: Город: {{$parseTree._City.name}}
    #             go!: /PlayTheGame/ask
                
    #         state: NoMatch
    #             event!: noMatch
    #             a: Это не город!
    
    #  state: stop
    #     q!: $regex</stop>
    #     a: Вы сдались? Набрано всего {{$session.score}} очков, для победы нужно набрать {{$session.score + 1}}.
    #     go!: /Start
    