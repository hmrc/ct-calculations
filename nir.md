# Impact of NIR on CT600 boxes

## Northern Ireland Section
### B5 NI Trading Activity

### B6 SME

### B7 NI Employer

### B8 Special Circumstances

The above boxes have been implemented and added to the HMTL and CT600 XML.

## About this return

### B125 Northern Ireland - form CT600G
CATO will not be supporting this form so this will always be false.




### B285 Total losses carried forward and claimed against total profits
    
    Old Calculation: B285 = CP263 = CP997 + CP283b
    
    New box: B285a - Total losses carried forward and claimed against trading profit
        B285a = CP283b
    New box: B285b - Total losses carried forward and claimed against NON trading profit
        B285b = CP997

    B285 = B285a + B285b


### B295 Total of deductions and reliefs

    New box: B295a - total deductions and reliefs against trading profit
        B295a = B275 + B285a
    New box: B295b - total deductions and reliefs against NON trading profit
        B295b = sum (B240 to B265) + B285b + 290

    B295 = B295a + B295b

### B300 Profits before qualifying donations and group relief

    New box: B300a - trading profit before qualifying donations and group relief
    New box: B300b - non trading profit before qualifying donations and group relief

    B300 = B300a + B300b

### B315 Profits chargeable to Corporations Tax

    New box: B315a - trading profits chargeable to CT
        B315a = B300a - B305

    New box: B315b - non-trading profits chargeable to CT
        B315b = B300b

    B315 = B315a + B315b

### B325 Northern Ireland profits included

    if (qualifies and accepts NIR) B325 = B315a else = 0
