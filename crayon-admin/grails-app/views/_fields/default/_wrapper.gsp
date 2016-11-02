<div class="form-group ${invalid ? 'has-error has-feedback' : required && value ? 'has-success has-feedback' : ''}">
    <label class="control-label" for="${property}">${label}</label>
    <f:input bean="${bean}" property="${property}" class="form-control" autocomplete="off" />
</div>