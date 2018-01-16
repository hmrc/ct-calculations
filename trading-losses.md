# Trading Losses

## Losses from a previous period

### CPQ17 (existing box)
Do have losses from a previous period?

Type: Boolean

Validation:
* Required if CP117 > 0
* Cannot exist if CP117 == 0
* Cannot exist if CPQ19 exists

# CP281 (existing box) 
What is the total value of your trading losses from previous accounting periods?

Type: Int

Validation:
* Required if CPQ17 is true
* Cannot exist of CPQ17 is empty or false
* Max 99999999
* Min 1
* Must equal CP281a + CP281b 

### CP281a (existing box) 
How much of those losses are from before 1st April 2017?

Type: Int

Validation: 
* Required if CPQ17 is true and loss reform applies
* Cannot exist if CPQ17 is false or loss reform does not apply
* Max 99999999
* Min 0
* Must equal CP283a + CP288a

### CP281b (existing box) 
How much of those losses arise on or after 1st April 2017?

Type: Int

Validation: 
* Required if CPQ17 is true and loss reform applies
* Cannot exist if CPQ17 is false or loss reform does not apply
* Max 99999999
* Min 0
* Must equal CP283b + CP288b + CP997

# CP283 (existing box) 
Losses brought forward used against trading profit

Type: Int

Calculated:
* if CP283a OR CP283b is non empty then CP283 = CP283a + CP293b 
* else if cpq17 is true then CP283 = min (CP281, CP282)

### CP283a (existing box) 
Losses brought forward from before 01/04/2017 used against trading profit

Type: Int

Validation:
* Required is CP281a > 0
* Max 99999999
* Min 0

### CP283b (existing box) 
Losses brought forward from on or after 01/04/2017 used against trading profit

Type: Int

Validation: 
* Required if CP281b is positive
* Max 99999999
* Min 0

# CP288 (existing box) 
Losses Carried forward

Type: Int

Calculated:
* max (CP281 + CP118 - CP283 - CP998 - CP997 - CP287, 0)

### CP288a (existing box) 
Losses carried forward from before 01/04/2017

Type: Int

Validation:
* Required is CP281a > 0
* Max 99999999
* Min 0

### CP288b (existing box) 
Losses carried forward from on or after 01/04/2017

Type: Int

Validation: 
* Required if CP281b is positive
* Max 99999999
* Min 0


### CP997 (existing box) 
Losses from previous AP after 01/04/2017 set against non trading profits this AP

Type: Int

Validation: 
* Required if CP281b is positive
* Max 99999999
* Min 0
* Must be <= CATO01

### CP??? (New box)
`NI Trading` Losses from previous AP after 01/04/2017 set against non trading profits this AP


## Global Errors
// sum of losses brought forward (checked by CP283a and CP283b)
* Fail if CP283a + CP283b > CP117
